package br.com.ezschedule.apischedule.service;

import br.com.ezschedule.apischedule.adapter.JsonResponseAdapter;
import br.com.ezschedule.apischedule.model.Credentials;
import br.com.ezschedule.apischedule.model.DtoClasses.PixRequest;
import br.com.ezschedule.apischedule.model.DtoClasses.Response.ReportResponse;
import br.com.ezschedule.apischedule.model.DtoClasses.UpdatePixReport;
import br.com.ezschedule.apischedule.model.DtoClasses.efi.PixResponse;
import br.com.ezschedule.apischedule.model.Report;
import br.com.ezschedule.apischedule.repository.ReportRepository;
import br.com.gerencianet.gnsdk.Gerencianet;
import br.com.gerencianet.gnsdk.exceptions.GerencianetException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PixService {

    @Autowired
    ReportRepository reportRepository;
    private static final Credentials credentials = new Credentials();
    private static JSONObject options = new JSONObject();

    static {
        options.put("client_id", credentials.getClientId());
        options.put("client_secret", credentials.getClientSecret());
        options.put("certificate", credentials.getCertificate());
        options.put("sandbox", credentials.isSandbox());
    }

    public ResponseEntity<PixResponse> createPix(PixRequest pix) {
        try {
            Gerencianet gn = new Gerencianet(options);
            JSONObject response = gn.call("pixCreateImmediateCharge", new HashMap<String, String>(), pix.getObjectAsJson(credentials.getPixKey()));

            System.out.println(response);

            Report report = new Report(
                    response.getString("txid"),
                    "",
                    response.getString("solicitacaoPagador"),
                    "salão",
                    response.getString("status"),
                    null,
                    null,
                    pix.getCondominium(),
                    pix.getTenant()
            );

            reportRepository.save(report);

            int id = response.getJSONObject("loc").getInt("id");

            HashMap<String, String> params = new HashMap<String, String>();
            params.put("id", Integer.toString(id));

            Map<String, Object> secondResponse = gn.call("pixGenerateQRCode", params, new HashMap<String, Object>());

            PixResponse pixResponse = new PixResponse(
                    secondResponse.get("qrcode").toString(),
                    secondResponse.get("imagemQrcode").toString(),
                    secondResponse.get("linkVisualizacao").toString()
            );

            return ResponseEntity.status(200).body(pixResponse);
        } catch (GerencianetException e) {
            System.out.println(e.getError());
            System.out.println(e.getErrorDescription());
            return ResponseEntity.status(407).build();
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(400).build();
        }
    }

    public ResponseEntity<List<ReportResponse>> getAllPixAttempts() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("inicio", "2023-01-01T16:01:35Z");
        params.put("fim", "2023-12-31T16:01:35Z");

        try {
            List<Report> allReports = reportRepository.findAll();
            if (allReports.isEmpty()) {
                return ResponseEntity.status(204).build();
            }
            Gerencianet gn = new Gerencianet(options);
            JSONObject response = gn.call("pixListCharges", params, new JSONObject());

            List<Report> ongoingReports = allReports.stream().filter(report -> report.getPaymentStatus().equals("ATIVA")).toList();


            JSONArray pixList = response.getJSONArray("cobs");

            List<Report> updateListPix = new ArrayList<>();

            for (int i = 0; i < pixList.length() - 1; i++) {
                JSONObject object = (JSONObject) pixList.get(i);

                List<Report> replicaReports = ongoingReports;
                replicaReports.stream().filter(r -> r.getId().equals(object.getString("txid"))).forEach(r -> {
                    if (object.getString("status").equals("CONCLUIDA")) {
                        JSONObject pixObject = (JSONObject) object.getJSONArray("pix").get(0);
                        UpdatePixReport updatePixReport = new UpdatePixReport(
                                object.getString("txid"),
                                object.getString("status"),
                                pixObject.getString("horario"),
                                pixObject.getString("endToEndId")
                        );

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        String time = updatePixReport.getPaymentTime().replace("T", " ");
                        String newTime = time.substring(0, time.indexOf("."));
                        LocalDateTime dateTime = LocalDateTime.parse(newTime, formatter);

                        r.setPaymentTime(dateTime);
                        r.setPaymentStatus(updatePixReport.getStatus());
                        r.setInvoiceNumber(updatePixReport.getInvoiceNumber());
                        updateListPix.add(r);
                    }
                });
            }
            return ResponseEntity.status(200).body(JsonResponseAdapter.listReportResponse(updateListPix));
        } catch (GerencianetException e) {
            System.out.println(e.getError());
            System.out.println(e.getErrorDescription());
            return ResponseEntity.status(400).build();
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(400).build();
        }
    }
}

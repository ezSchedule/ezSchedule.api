package br.com.ezschedule.apischedule.service;

import br.com.ezschedule.apischedule.model.Credentials;
import br.com.ezschedule.apischedule.model.DtoClasses.PixRequest;
import br.com.ezschedule.apischedule.model.DtoClasses.efi.PixResponse;
import br.com.gerencianet.gnsdk.Gerencianet;
import br.com.gerencianet.gnsdk.exceptions.GerencianetException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PixService {

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
            System.out.println(pix.getObjectAsJson(credentials.getPixKey()));
            JSONObject response = gn.call("pixCreateImmediateCharge", new HashMap<String, String>(), pix.getObjectAsJson(credentials.getPixKey()));

            System.out.println(response);

            int id = response.getJSONObject("loc").getInt("id");

            HashMap<String, String> params = new HashMap<String, String>();
            params.put("id", Integer.toString(id));

            Map<String, Object> secondResponse = gn.call("pixGenerateQRCode", params, new HashMap<String, Object>());

            PixResponse pixResponse = new PixResponse(
                    response.getString("txid"),
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

    public ResponseEntity<Map<String, Object>> getAllPixAttempts() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("inicio", "2023-01-01T16:01:35Z");
        params.put("fim", "2023-12-31T16:01:35Z");

        try {
            Gerencianet gn = new Gerencianet(options);
            JSONObject response = gn.call("pixListCharges", params, new JSONObject());

            return ResponseEntity.status(200).body(response.toMap());
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

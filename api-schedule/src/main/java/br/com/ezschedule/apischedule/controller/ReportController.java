package br.com.ezschedule.apischedule.controller;

import br.com.ezschedule.apischedule.model.DtoClasses.ReportPaymentsDto;
import br.com.ezschedule.apischedule.model.DtoClasses.Response.ReportResponse;
import br.com.ezschedule.apischedule.model.Report;
import br.com.ezschedule.apischedule.service.ReportService;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Api(value = "Relatórios", produces = MediaType.APPLICATION_JSON_VALUE, tags = {"relatorios"}, description = "requisições relacionadas a relatórios")
@RestController
@RequestMapping("${uri.reports}")
public class ReportController {
    @Autowired
    private MongoClient mongoClient;
    @Autowired
    ReportService service;
    @ApiResponse(responseCode = "204", description = "Não há relatórios cadastrados.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "relatórios encontrados.")
    @GetMapping
    public ResponseEntity<List<ReportResponse>> showAllReports() {
        return service.findAll();
    }

    @ApiResponse(responseCode = "204", description = "Não há relatórios cadastrados.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "relatórios encontrados.")
    @GetMapping("/condominium/{id}")
    public ResponseEntity<List<ReportPaymentsDto>> showReportsCondominium(@PathVariable int id) {
        return service.findAllByCondominium(id);
    }

    @GetMapping("/tenant/{id}")
    public ResponseEntity<List<ReportPaymentsDto>> showReportsByTenant(@PathVariable int id) {
        return service.findAllByTenant(id);
    }

    @ApiResponse(responseCode = "201", description = "Relatório cadastrado", content = @Content(schema = @Schema(hidden = true)))
    @PostMapping
    public ResponseEntity<ReportResponse> newReport(@RequestBody @Valid Report report) {

        MongoDatabase database = this.mongoClient.getDatabase("ezschedule");
        MongoCollection collection = database.getCollection("logs");

        //Inserção de dados no MongoBd V1
//        Document reportMongo = new Document();
//        reportMongo.append("id", report.getId());
//        reportMongo.append("invoiceNumber", report.getInvoiceNumber());
//        reportMongo.append("productName", report.getProductName());
//        reportMongo.append("category", report.getCategory());
//        reportMongo.append("paymentStatus", report.getPaymentStatus());
//        reportMongo.append("paymentTime", report.getPaymentTime());
//        reportMongo.append("schedule", report.getSchedule());
//        reportMongo.append("condominium", report.getCondominium().getId());
//        collection.insertOne(reportMongo);

        return service.add(report);
    }

    @GetMapping("/condominium/no-payment/{id}")
    public ResponseEntity<List<ReportResponse>> findAllCondominiumReportsWNoPayment(@PathVariable int id){
        return service.findAllCondominiumReportsWNoPayment(id);
    }

    @PutMapping("/{id}/{status}/{paymentTime}")
    public ResponseEntity<Void> updateReport(@PathVariable int id, @PathVariable String status, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime paymentTime){
        return service.update(id,status,paymentTime);
    }

}

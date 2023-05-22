package br.com.ezschedule.apischedule.controller;

import br.com.ezschedule.apischedule.adapter.JsonResponseAdapter;
import br.com.ezschedule.apischedule.model.DtoClasses.ReportPaymentsDto;
import br.com.ezschedule.apischedule.model.DtoClasses.Response.ReportResponse;
import br.com.ezschedule.apischedule.model.Report;
import br.com.ezschedule.apischedule.repository.ReportRepository;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Relatórios", produces = MediaType.APPLICATION_JSON_VALUE, tags = {"relatorios"}, description = "requisições relacionadas a relatórios")
@RestController
@RequestMapping("${uri.report}")
public class ReportController {

    @Autowired
    ReportRepository reportRepository;

    @ApiResponse(responseCode = "204", description =
            "Não há relatórios cadastrados.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "relatórios encontrados.")
    @GetMapping
    public ResponseEntity<List<ReportResponse>> showAllReports(){
        List<Report> allPosts = reportRepository.findAll();
        if(allPosts.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(JsonResponseAdapter.listReportResponse(allPosts));
    }

    @ApiResponse(responseCode = "204", description =
            "Não há relatórios cadastrados.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "relatórios encontrados.")
    @GetMapping("/condominium")
    public ResponseEntity<List<ReportPaymentsDto>> showReportsCondominium(@RequestParam int id){
        List<ReportPaymentsDto> allPosts = reportRepository.findAllReportsCondominium(id);
        if(allPosts.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(allPosts);
    }

    @ApiResponse(responseCode = "201", description =
            "Relatório cadastrado", content = @Content(schema = @Schema(hidden = true)))
    @PostMapping
    public ResponseEntity<ReportResponse> newReport(@RequestBody @Valid Report report){
        reportRepository.save(report);
        return ResponseEntity.status(201).build();
    }
}

package br.com.ezschedule.apischedule.controller;

import br.com.ezschedule.apischedule.adapter.JsonResponseAdapter;
import br.com.ezschedule.apischedule.model.DtoClasses.ReportDTO;
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
import java.util.Optional;

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
    public ResponseEntity<List<ReportDTO>> showAllReports(){
        List<Report> allPosts = reportRepository.findAll();
        if(allPosts.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(JsonResponseAdapter.listReportDTO(allPosts));
    }

    @ApiResponse(responseCode = "201", description =
            "Relatório cadastrado", content = @Content(schema = @Schema(hidden = true)))
    @PostMapping
    public ResponseEntity<ReportDTO> newReport(@RequestBody @Valid Report report){
        reportRepository.save(report);
        return ResponseEntity.status(200).body(JsonResponseAdapter.reportDTO(report));
    }


}

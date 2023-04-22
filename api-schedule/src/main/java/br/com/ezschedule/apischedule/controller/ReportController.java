package br.com.ezschedule.apischedule.controller;

import br.com.ezschedule.apischedule.adapter.JsonResponseAdapter;
import br.com.ezschedule.apischedule.model.DtoClasses.ReportDTO;
import br.com.ezschedule.apischedule.model.Report;
import br.com.ezschedule.apischedule.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${uri.report}")
public class ReportController {

    @Autowired
    ReportRepository reportRepository;

    @GetMapping
    public ResponseEntity<List<ReportDTO>> showAllReports(){
        List<Report> allPosts = reportRepository.findAll();
        if(allPosts.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(JsonResponseAdapter.listReportDTO(allPosts));
    }

    @PostMapping
    public ResponseEntity<ReportDTO> newReport(@RequestBody @Valid Report report){
        reportRepository.save(report);
        return ResponseEntity.status(200).body(JsonResponseAdapter.reportDTO(report));
    }


}

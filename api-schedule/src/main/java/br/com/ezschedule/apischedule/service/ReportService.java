package br.com.ezschedule.apischedule.service;

import br.com.ezschedule.apischedule.adapter.JsonResponseAdapter;
import br.com.ezschedule.apischedule.model.DtoClasses.ReportPaymentsDto;
import br.com.ezschedule.apischedule.model.DtoClasses.Response.ReportResponse;
import br.com.ezschedule.apischedule.model.Report;
import br.com.ezschedule.apischedule.repository.ReportRepository;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    ReportRepository reportRepository;

    public ResponseEntity<List<ReportResponse>> findAll() {
        List<Report> allPosts = reportRepository.findAll();
        if (allPosts.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(JsonResponseAdapter.listReportResponse(allPosts));
    }

    public ResponseEntity<List<ReportPaymentsDto>> findAllByCondominium(int id) {
        List<ReportPaymentsDto> allPosts = reportRepository.findAllReportsCondominium(id);
        if (allPosts.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(allPosts);
    }

    public ResponseEntity<List<ReportResponse>> findAllByTenant(int id) {
        List<Report> allPosts = reportRepository.findAllReportsByTenant(id);
        if (allPosts.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(JsonResponseAdapter.listReportResponse(allPosts));
    }

    public ResponseEntity<ReportResponse> add(Report report) {
        reportRepository.save(report);
        return ResponseEntity.status(201).build();
    }
}

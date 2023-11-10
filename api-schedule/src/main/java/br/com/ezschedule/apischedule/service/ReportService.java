package br.com.ezschedule.apischedule.service;

import br.com.ezschedule.apischedule.adapter.JsonResponseAdapter;
import br.com.ezschedule.apischedule.model.DtoClasses.ReportPaymentsDto;
import br.com.ezschedule.apischedule.model.DtoClasses.Response.ReportResponse;
import br.com.ezschedule.apischedule.model.Report;
import br.com.ezschedule.apischedule.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {
    @Autowired
    ReportRepository reportRepository;

    public ResponseEntity<List<ReportResponse>> findAll() {
        List<Report> allReports = reportRepository.findAll();
        if (allReports.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(JsonResponseAdapter.listReportResponse(allReports));
    }

    public ResponseEntity<List<ReportPaymentsDto>> findAllByCondominium(int id) {
        List<ReportPaymentsDto> allPosts = reportRepository.findAllReportsCondominium(id);
        if (allPosts.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(allPosts);
    }

    public ResponseEntity<List<ReportPaymentsDto>> findAllByTenant(int id) {
        List<ReportPaymentsDto> allPosts = reportRepository.findAllReportsByTenant(id);
        if (allPosts.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(allPosts);
    }

    public ResponseEntity<ReportResponse> add(Report report) {
        reportRepository.save(report);
        return ResponseEntity.status(201).build();
    }

    public ResponseEntity<List<ReportResponse>> findAllCondominiumReportsWNoPayment(int id) {
        if (reportRepository.verifyIfCondominiumExists(id) > 0) {
            return ResponseEntity.status(200).body(JsonResponseAdapter.listReportResponse(reportRepository.findAllCondominiumReportsWNoPayment(id)));
        }
        return ResponseEntity.status(404).build();
    }


    public ResponseEntity<Void> update(int id, String status, LocalDateTime paymentTime) {
        if (reportRepository.existsById(id)) {
            Optional<Report> report = reportRepository.findById(id);
            if (report.isPresent()) {
                report.get().setPaymentStatus(status);
                report.get().setPaymentTime(paymentTime);
                reportRepository.save(report.get());
                return ResponseEntity.status(200).build();
            }
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(404).build();
    }
}

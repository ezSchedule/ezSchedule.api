package br.com.ezschedule.apischedule.controller;

import br.com.ezschedule.apischedule.model.DtoClasses.PixRequest;
import br.com.ezschedule.apischedule.model.DtoClasses.Response.ReportResponse;
import br.com.ezschedule.apischedule.model.DtoClasses.efi.PixResponse;
import br.com.ezschedule.apischedule.model.Report;
import br.com.ezschedule.apischedule.service.PixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("${uri.pix}")
@RestController
public class PixController {

    @Autowired
    PixService pixService;

    @PostMapping
    public ResponseEntity<PixResponse> createPix(@RequestBody @Valid PixRequest pix) {
        return pixService.createPix(pix);
    }

    @PostMapping("/list")
    public ResponseEntity<List<ReportResponse>> list(){
        return pixService.getAllPixAttempts();
    }

}

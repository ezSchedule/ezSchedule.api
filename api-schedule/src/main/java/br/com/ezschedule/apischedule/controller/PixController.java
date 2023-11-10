package br.com.ezschedule.apischedule.controller;

import br.com.ezschedule.apischedule.model.DtoClasses.PixRequest;
import br.com.ezschedule.apischedule.model.DtoClasses.Response.ReportResponse;
import br.com.ezschedule.apischedule.model.DtoClasses.efi.PixResponse;
import br.com.ezschedule.apischedule.model.Report;
import br.com.ezschedule.apischedule.service.PixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("${uri.pix}")
@RestController
public class PixController {

    @Autowired
    PixService pixService;

    @GetMapping
    public ResponseEntity<PixResponse> createPix(@RequestBody @Valid PixRequest pix) {
        return pixService.createPix(pix);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ReportResponse>> list(){
        return pixService.getAllPixAttempts();
    }

}

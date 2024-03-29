package br.com.ezschedule.apischedule.controller;

import br.com.ezschedule.apischedule.model.DtoClasses.PixRequest;
import br.com.ezschedule.apischedule.model.DtoClasses.efi.PixResponse;
import br.com.ezschedule.apischedule.service.PixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RequestMapping("${uri.pix}")
@RestController
public class PixController {

    @Autowired
    PixService pixService;

    @PostMapping
    public ResponseEntity<PixResponse> createPix(@RequestBody @Valid PixRequest pix) {
        return pixService.createPix(pix);
    }

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> list() {
        return pixService.getAllPixAttempts();
    }

    @GetMapping("/test")
    public ResponseEntity<String> version() {
        return ResponseEntity.status(200).body("version 1.0");
    }

    @GetMapping("/list/{cpf}")
    public ResponseEntity<Map<String, Object>> listCpf(@PathVariable String cpf) {
        return pixService.getAllPixAttemptsByCpf(cpf);
    }

}

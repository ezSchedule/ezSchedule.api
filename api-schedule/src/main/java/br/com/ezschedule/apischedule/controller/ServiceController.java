package br.com.ezschedule.apischedule.controller;


import br.com.ezschedule.apischedule.model.DtoClasses.Response.ServiceResponse;
import br.com.ezschedule.apischedule.model.DtoClasses.Response.TenantResponse;
import br.com.ezschedule.apischedule.model.DtoClasses.ServiceDTO;
import br.com.ezschedule.apischedule.model.Service;
import br.com.ezschedule.apischedule.service.Sservice;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Api(value = "Serviço", produces = MediaType.APPLICATION_JSON_VALUE, tags = {"servico"}, description = "requisições relacionadas a serviço")
@RestController
@RequestMapping("${uri.services}")
public class ServiceController {

    @Autowired
    Sservice service;

    @ApiResponse(responseCode = "204", description = "Não há serviços cadastrados.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "serviços encontrados.")
    @GetMapping()
    public ResponseEntity<List<ServiceResponse>> listAllServices() {
        return service.listAll();
    }

    @ApiResponse(responseCode = "204", description = "Não há serviços cadastrados.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "serviços encontrados.")
    @GetMapping("/condominium/{id}")
    public ResponseEntity<List<ServiceResponse>> listService(@PathVariable int id) {
        return service.listAllByCondominium(id);
    }

    @ApiResponse(responseCode = "201", description = "Serviço cadastrado", content = @Content(schema = @Schema(hidden = true)))
    @PostMapping
    public ResponseEntity<Service> addService(@RequestBody @Valid Service s) {
        return service.add(s);
    }


    @ApiResponse(responseCode = "404", description = "Não há serviços cadastrados.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "serviços atualizado.")
    @PutMapping("/update")
    public ResponseEntity<ServiceDTO> updateService(@RequestParam int id, @RequestParam String serviceName) {
        return service.update(id, serviceName);
    }

    @ApiResponse(responseCode = "404", description = "Não há serviços cadastrados.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "serviços deletado.")
    @DeleteMapping("/delete")
    public ResponseEntity<ServiceDTO> deleteService(@RequestParam int id) {
        return service.delete(id);
    }

    @GetMapping("/tenant")
    public ResponseEntity<List<TenantResponse>> tenantsList(@RequestParam int id) {
        return service.listTenantsByCondominium(id);
    }

}

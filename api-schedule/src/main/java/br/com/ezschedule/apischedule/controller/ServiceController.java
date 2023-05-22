package br.com.ezschedule.apischedule.controller;


import br.com.ezschedule.apischedule.adapter.JsonResponseAdapter;
import br.com.ezschedule.apischedule.model.DtoClasses.Response.ServiceResponse;
import br.com.ezschedule.apischedule.model.DtoClasses.Response.TenantResponse;
import br.com.ezschedule.apischedule.model.DtoClasses.ServiceDTO;
import br.com.ezschedule.apischedule.model.ObjectList;
import br.com.ezschedule.apischedule.model.Service;
import br.com.ezschedule.apischedule.model.Tenant;
import br.com.ezschedule.apischedule.repository.ServiceRepository;
import br.com.ezschedule.apischedule.repository.TenantRepository;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.Collator;
import java.util.*;

@Api(value = "Serviço", produces = MediaType.APPLICATION_JSON_VALUE, tags = {"servico"}, description = "requisições relacionadas a serviço")
@RestController
@RequestMapping("${uri.services}")
public class ServiceController {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private TenantRepository tenantRepository;

    ObjectList<Service> servicevector = new ObjectList<Service>(100);
    ObjectList<Service> orderedVector = new ObjectList<>(100);

    @ApiResponse(responseCode = "204", description =
            "Não há serviços cadastrados.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "serviços encontrados.")
    @GetMapping("/tenant")
    public ResponseEntity<List<TenantResponse>> tenantsList(@RequestParam int id) {
        List<Tenant> listTenants = this.tenantRepository.findAllTenantsCondominium(id);
        if (listTenants.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(JsonResponseAdapter.listTenantResponse(listTenants)
        );
    }

    @GetMapping
    public ResponseEntity<List<ServiceResponse>> listService(@RequestParam int id) {
        List<Service> services = this.serviceRepository.listService(id);
        if (!services.isEmpty())
            return ResponseEntity.status(200).body(JsonResponseAdapter.listServiceResponse(services));
        return ResponseEntity.status(204).build();
    }

    @ApiResponse(responseCode = "201", description =
            "Serviço cadastrado", content = @Content(schema = @Schema(hidden = true)))
    @PostMapping
    public ResponseEntity<Service> addService(@RequestBody @Valid Service s) {
        this.serviceRepository.save(s);
        return ResponseEntity.status(201).build();
    }

    @ApiResponse(responseCode = "200", description =
            "Serviços encontrados por nome.", content = @Content(schema = @Schema(hidden = true)))
    @GetMapping("/name")
    public ResponseEntity<List<ServiceDTO>> showListOrderedByName(@RequestParam String name) {
        List<Service> listService = this.serviceRepository.findAllByServiceName(name);
        if (listService.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        for (int i = 0; i < listService.size(); i++) {
            Optional<Service> serviceName = this.serviceRepository.findById(listService.get(i).getId());
            servicevector.addObject(serviceName.get());
        }

        for (int i = 0; i < servicevector.getSize(); i++) {
            orderedVector.addObject(servicevector.getByIndex(i));
        }

        for (int i = 0; i < orderedVector.getSize() - 1; i++) {
            for (int j = orderedVector.getSize() - 1; j > i; j--) {
                Service iObject = orderedVector.getByIndex(i);
                Service jObject = orderedVector.getByIndex(j);

                if (iObject.getServiceName().compareTo(jObject.getServiceName()) > 0) {
                    Service aux = iObject;
                    orderedVector.substituteByIndex(i, jObject);
                    orderedVector.substituteByIndex(j, aux);
                }
            }
        }
        return ResponseEntity.status(200).body(JsonResponseAdapter.serviceArrayDTO(orderedVector.getSize(), orderedVector));
    }

    //Método que precisa de conversa com os integrantes da Ezschedule!!!
    @ApiResponse(responseCode = "404", description =
            "Não há serviços cadastrados.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "serviços encontrados.")
    @GetMapping("/binarySearch")
    public ResponseEntity<ServiceDTO> binarySearch(@RequestBody @Valid Service serviceOfChoice) {
        int meio;
        int inicio = 0;
        int fim = orderedVector.getSize();

        do {
            meio = (inicio + fim) / 2;
            Service currentService = orderedVector.getByIndex(meio);
            if (serviceOfChoice.getServiceName().equals(currentService.getServiceName())) {
                return ResponseEntity.status(200).body(JsonResponseAdapter.serviceDTO(orderedVector.getByIndex(meio)));
            } else if (serviceOfChoice.getServiceName().compareTo(orderedVector.getByIndex(meio).getServiceName()) < 0) {
                fim = meio - 1;
            } else {
                inicio = meio + 1;
            }
        } while (inicio <= fim);

        return ResponseEntity.status(404).build();
    }

    @ApiResponse(responseCode = "404", description =
            "Não há serviços cadastrados.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "serviços atualizado.")
    @PutMapping
    public ResponseEntity<ServiceDTO> updateService(@RequestParam int id, @RequestParam String serviceName) {
        Optional<Service> serviceOpt = this.serviceRepository.findById(id);
        if (serviceOpt.isPresent()) {
            this.serviceRepository.updateService(id, serviceName);
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.notFound().build();
    }

    @ApiResponse(responseCode = "404", description =
            "Não há serviços cadastrados.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "serviços deletado.")
    @DeleteMapping
    public ResponseEntity<ServiceDTO> deleteService(@RequestParam int id) {
        Optional<Service> serviceOpt = this.serviceRepository.findById(id);
        if (serviceOpt.isPresent()) {
            this.serviceRepository.deleteById(id);
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.notFound().build();
    }

}

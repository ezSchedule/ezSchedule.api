package br.com.ezschedule.apischedule.service;

import br.com.ezschedule.apischedule.adapter.JsonResponseAdapter;
import br.com.ezschedule.apischedule.model.DtoClasses.Response.ServiceResponse;
import br.com.ezschedule.apischedule.model.DtoClasses.Response.TenantResponse;
import br.com.ezschedule.apischedule.model.DtoClasses.ServiceDTO;
import br.com.ezschedule.apischedule.model.Tenant;
import br.com.ezschedule.apischedule.repository.ServiceRepository;
import br.com.ezschedule.apischedule.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class Sservice {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private TenantRepository tenantRepository;

    public ResponseEntity<List<ServiceResponse>> listAll() {
        List<br.com.ezschedule.apischedule.model.Service> services = this.serviceRepository.findAll();
        if (!services.isEmpty())
            return ResponseEntity.status(200).body(JsonResponseAdapter.listServiceResponse(services));
        return ResponseEntity.status(204).build();
    }

    public ResponseEntity<List<ServiceResponse>> listAllByCondominium(int id) {
        List<br.com.ezschedule.apischedule.model.Service> services = this.serviceRepository.listServiceByCondominium(id);
        if (!services.isEmpty())
            return ResponseEntity.status(200).body(JsonResponseAdapter.listServiceResponse(services));
        return ResponseEntity.status(200).body(new ArrayList<>());
    }

    public ResponseEntity<br.com.ezschedule.apischedule.model.Service> add(br.com.ezschedule.apischedule.model.Service s) {
        this.serviceRepository.save(s);
        return ResponseEntity.status(201).build();
    }

    public ResponseEntity<ServiceDTO> update(int id, String serviceName) {
        Optional<br.com.ezschedule.apischedule.model.Service> serviceOpt = this.serviceRepository.findById(id);
        if (serviceOpt.isPresent()) {
            this.serviceRepository.updateService(id, serviceName);
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<ServiceDTO> delete(@RequestParam int id) {
        Optional<br.com.ezschedule.apischedule.model.Service> serviceOpt = this.serviceRepository.findById(id);
        if (serviceOpt.isPresent()) {
            this.serviceRepository.deleteById(id);
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/tenant")
    public ResponseEntity<List<TenantResponse>> listTenantsByCondominium(@RequestParam int id) {
        List<Tenant> listTenants = this.tenantRepository.findAllTenantsCondominium(id);
        if (listTenants.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(JsonResponseAdapter.listTenantResponse(listTenants));
    }

}

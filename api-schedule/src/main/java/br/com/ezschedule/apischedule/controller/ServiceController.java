package br.com.ezschedule.apischedule.controller;


import br.com.ezschedule.apischedule.adapter.JsonResponseAdapter;
import br.com.ezschedule.apischedule.model.DtoClasses.ServiceDTO;
import br.com.ezschedule.apischedule.model.ObjectList;
import br.com.ezschedule.apischedule.model.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.Collator;
import java.util.*;

@RestController
@RequestMapping("${uri.services}")
public class ServiceController {

     ObjectList<Service> servicevector = new ObjectList<Service>(100);
    ObjectList<Service> orderedVector = new ObjectList<>(100);

    @GetMapping
    public ResponseEntity<List<ServiceDTO>> showList() {
        if (servicevector.getSize() == 0) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(
                JsonResponseAdapter.serviceArrayDTO(
                        servicevector.getSize(),
                        servicevector
                        )
        );
    }

    @PostMapping
    public ResponseEntity<Service> addService(@RequestBody @Valid Service s) {
        return ResponseEntity.status(201).body(servicevector.addObject(s));
    }

    @GetMapping("/nome")
    public ResponseEntity<List<ServiceDTO>> showListOrderedByName() {
      for(int i =0;i< servicevector.getSize();i++){
          orderedVector.addObject(servicevector.getByIndex(i));
      }

        for(int i =0;i < orderedVector.getSize() -1;i++){
            for(int j =orderedVector.getSize()-1;j > i;j--) {
                Service iObject = orderedVector.getByIndex(i);
                Service jObject = orderedVector.getByIndex(j);

                if (iObject.getServiceName().compareTo(jObject.getServiceName()) > 0) {
                    Service aux = iObject;
                    orderedVector.substituteByIndex(i,jObject);
                    orderedVector.substituteByIndex(j,aux);
                }
            }
        }
        return ResponseEntity.status(200).body(JsonResponseAdapter.serviceArrayDTO(orderedVector.getSize(), orderedVector));
    }

    @GetMapping("/binarySearch")
    public ResponseEntity<ServiceDTO> binarySearch(@RequestBody @Valid Service serviceOfChoice){
        int meio;
        int inicio = 0;
        int fim = orderedVector.getSize();

        do{
            meio = (inicio + fim) /2;
            Service currentService = orderedVector.getByIndex(meio);
            if(serviceOfChoice.getServiceName().equals(currentService.getServiceName())){
                return ResponseEntity.status(200).body(JsonResponseAdapter.serviceDTO(orderedVector.getByIndex(meio)));
            }
            else if(serviceOfChoice.getServiceName().compareTo(orderedVector.getByIndex(meio).getServiceName()) < 0) {
                fim =meio -1;
            }
            else{
                inicio = meio +1;
            }
        }while(inicio <= fim);

        return ResponseEntity.status(404).build();
    }




}

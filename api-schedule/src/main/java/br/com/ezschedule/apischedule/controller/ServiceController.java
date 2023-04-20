package br.com.ezschedule.apischedule.controller;


import br.com.ezschedule.apischedule.model.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.Collator;
import java.util.Locale;

@RestController
@RequestMapping("${uri.syndicate}/services")
public class ServiceController<T> {
    private final T[] vetor;
    private Integer nElement;

    Collator brCollator = Collator.getInstance(new Locale("pt","BR"));

    public ServiceController() {
        this.vetor = (T[]) new Object[100];
        this.nElement = 0;
    }


    @GetMapping
    public ResponseEntity<T[]> showList() {
        if (nElement == 0) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(vetor);
    }

    @PostMapping
    public ResponseEntity<T> addService(@RequestBody T s) {
        vetor[nElement++] = s;
        return ResponseEntity.status(201).body(vetor[nElement]);
    }

    @GetMapping("/nome")
    public void showListOrderedByName() {
        for (int i = 0; i < nElement; i++) {
            System.out.println(vetor[i]);
        }
    }
}

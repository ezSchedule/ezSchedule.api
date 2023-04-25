package br.com.ezschedule.apischedule.controller;

import br.com.ezschedule.apischedule.adapter.JsonResponseAdapter;
import br.com.ezschedule.apischedule.model.Condominium;
import br.com.ezschedule.apischedule.model.DtoClasses.CondominiumResponse.CondominiumResponseDto;
import br.com.ezschedule.apischedule.repository.CondominumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("${uri.condominium}")
public class CondominumController {

    @Autowired
    CondominumRepository condominumRepository;

    @GetMapping
    public ResponseEntity<List<CondominiumResponseDto>> listar(){
        List<Condominium> condominiumList = condominumRepository.findAll();

        if(!condominiumList.isEmpty()){
            return ResponseEntity.status(200).body(JsonResponseAdapter.listCondominumDTO(condominiumList));
        }
        return ResponseEntity.status(204).build();
    }

    @PostMapping
    public ResponseEntity<Condominium> addCondominum(@RequestBody @Valid Condominium c){
        condominumRepository.save(c);

        return ResponseEntity.status(201).body(c);
    }

}
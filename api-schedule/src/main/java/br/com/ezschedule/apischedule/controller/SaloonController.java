package br.com.ezschedule.apischedule.controller;

import br.com.ezschedule.apischedule.adapter.JsonResponseAdapter;
import br.com.ezschedule.apischedule.model.DtoClasses.UpdateSaloonForm;
import br.com.ezschedule.apischedule.model.Saloon;
import br.com.ezschedule.apischedule.repository.SaloonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("${uri.saloons}")
public class SaloonController {
   @Autowired
    SaloonRepository saloonRepository;

    @GetMapping
    public ResponseEntity<List<Saloon>> showAllSaloons(){
        List<Saloon> allSaloons = saloonRepository.findAll();
        if(allSaloons.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(allSaloons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Saloon> showASaloonById(@PathVariable  int id){
        Optional<Saloon> saloon = saloonRepository.findById(id);
        if(saloon.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(saloon.get());
    }

    @PostMapping
    public ResponseEntity<Saloon> newSaloon(@RequestBody @Valid Saloon post){
        saloonRepository.save(post);
        return ResponseEntity.status(200).body(post);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Saloon> updateSaloonById(@RequestBody @Valid UpdateSaloonForm newSaloon, @PathVariable int id){
//        Optional<Saloon> oldSaloon = saloonRepository.findById(id);
//        if(oldSaloon.isPresent()){
//            Saloon updatedSaloon = JsonResponseAdapter.saloonDTO(newSaloon,id,oldSaloon.get().getCondominium());
//            saloonRepository.save(updatedSaloon);
//            return ResponseEntity.status(200).body(updatedSaloon);
//        }
//        return ResponseEntity.status(404).build();
//    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSaloonById(@PathVariable int id){
        if(saloonRepository.existsById(id)){
            saloonRepository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }


}

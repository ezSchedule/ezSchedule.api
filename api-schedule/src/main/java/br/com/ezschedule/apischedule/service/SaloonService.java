package br.com.ezschedule.apischedule.service;

import br.com.ezschedule.apischedule.adapter.JsonResponseAdapter;
import br.com.ezschedule.apischedule.model.DtoClasses.Response.SaloonResponse;
import br.com.ezschedule.apischedule.model.DtoClasses.UpdateResponse.UpdateSaloonForm;
import br.com.ezschedule.apischedule.model.Saloon;
import br.com.ezschedule.apischedule.repository.SaloonRepository;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class SaloonService {

    @Autowired
    SaloonRepository saloonRepository;

    public ResponseEntity<List<SaloonResponse>> findAll() {
        List<Saloon> allSaloons = saloonRepository.findAll();
        if (allSaloons.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(JsonResponseAdapter.listSaloonResponse(allSaloons));
    }

    public ResponseEntity<List<SaloonResponse>> findAllByCondominium(int id) {
        List<Saloon> allSaloons = saloonRepository.findAllSaloonByCondominium(id);
        if (allSaloons.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(JsonResponseAdapter.listSaloonResponse(allSaloons));
    }

    public ResponseEntity<SaloonResponse> findById(int id) {
        Optional<Saloon> saloon = saloonRepository.findById(id);
        if (!saloon.isPresent()) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(JsonResponseAdapter.saloonResponse(saloon.get()));
    }

    public ResponseEntity<Saloon> add(Saloon post) {
        saloonRepository.save(post);
        return ResponseEntity.status(201).build();
    }


    public ResponseEntity<SaloonResponse> update(UpdateSaloonForm newSaloon, int id) {
        Optional<Saloon> oldSaloon = saloonRepository.findById(id);
        if (oldSaloon.isPresent()) {
            Saloon updatedSaloon = JsonResponseAdapter.UpdateSaloon(newSaloon, oldSaloon.get());
            saloonRepository.save(updatedSaloon);
            return ResponseEntity.status(200).body(JsonResponseAdapter.saloonResponse(updatedSaloon));
        }
        return ResponseEntity.status(404).build();
    }

    public ResponseEntity<Void> delete(int id) {
        if (saloonRepository.existsById(id)) {
            saloonRepository.deleteById(id);
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(404).build();
    }

}

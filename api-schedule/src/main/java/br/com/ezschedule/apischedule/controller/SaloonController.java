package br.com.ezschedule.apischedule.controller;

import br.com.ezschedule.apischedule.adapter.JsonResponseAdapter;
import br.com.ezschedule.apischedule.model.DtoClasses.SaloonDTO;
import br.com.ezschedule.apischedule.model.DtoClasses.UpdateSaloonForm;
import br.com.ezschedule.apischedule.model.Saloon;
import br.com.ezschedule.apischedule.repository.SaloonRepository;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Api(value = "Salão", produces = MediaType.APPLICATION_JSON_VALUE, tags = {"salao"}, description = "requisições relacionadas a salão de festas")
@RestController
@RequestMapping("${uri.saloons}")
public class SaloonController {
   @Autowired
    SaloonRepository saloonRepository;

    @ApiResponse(responseCode = "204", description =
            "Não há nenhum salão cadastrados.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "salão encontrado.")
    @GetMapping
    public ResponseEntity<List<SaloonDTO>> showAllSaloons(){
        List<Saloon> allSaloons = saloonRepository.findAll();
        if(allSaloons.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(JsonResponseAdapter.listSaloonDTO(allSaloons));
    }

    @ApiResponse(responseCode = "404", description =
            "Salão não encontrado.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "salão encontrado.")
    @GetMapping("/{id}")
    public ResponseEntity<SaloonDTO> showASaloonById(@PathVariable  int id){
        Optional<Saloon> saloon = saloonRepository.findById(id);
        if(saloon.isEmpty()){
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(JsonResponseAdapter.saloonDTO(saloon.get()));
    }

    @ApiResponse(responseCode = "201", description =
            "Salão cadastrado", content = @Content(schema = @Schema(hidden = true)))
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

    @ApiResponse(responseCode = "404", description =
            "salão não encontrado.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "salão deletado com sucesso.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSaloonById(@PathVariable int id){
        if(saloonRepository.existsById(id)){
            saloonRepository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }


}

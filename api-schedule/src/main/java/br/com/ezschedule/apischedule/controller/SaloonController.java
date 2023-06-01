package br.com.ezschedule.apischedule.controller;

import br.com.ezschedule.apischedule.adapter.JsonResponseAdapter;
import br.com.ezschedule.apischedule.model.DtoClasses.Response.SaloonResponse;
import br.com.ezschedule.apischedule.model.DtoClasses.UpdateResponse.UpdateSaloonForm;
import br.com.ezschedule.apischedule.model.Saloon;
import br.com.ezschedule.apischedule.repository.SaloonRepository;
import br.com.ezschedule.apischedule.service.SaloonService;
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
    SaloonService saloonService;

    @ApiResponse(responseCode = "204", description = "Não há nenhum salão cadastrados.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "salão encontrado.")
    @GetMapping
    public ResponseEntity<List<SaloonResponse>> showAllSaloons() {
        return saloonService.findAll();
    }

    @GetMapping("/condominium/{id}")
    public ResponseEntity<List<SaloonResponse>> showAllSaloonsByCondominium(@PathVariable int id) {
        return saloonService.findAllByCondominium(id);
    }

    @ApiResponse(responseCode = "404", description = "Salão não encontrado.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "salão encontrado.")
    @GetMapping("/{id}")
    public ResponseEntity<SaloonResponse> showASaloonById(@PathVariable int id) {
        return saloonService.findById(id);
    }

    @ApiResponse(responseCode = "201", description = "Salão cadastrado", content = @Content(schema = @Schema(hidden = true)))
    @PostMapping
    public ResponseEntity<Saloon> newSaloon(@RequestBody @Valid Saloon saloon) {
        return saloonService.add(saloon);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<SaloonResponse> updateSaloonById(@RequestBody @Valid UpdateSaloonForm newSaloon, @PathVariable int id){
        return saloonService.update(newSaloon,id);
    }

    @ApiResponse(responseCode = "404", description = "salão não encontrado.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "salão deletado com sucesso.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSaloonById(@PathVariable int id) {
        return saloonService.delete(id);
    }


}

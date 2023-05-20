package br.com.ezschedule.apischedule.controller;

import br.com.ezschedule.apischedule.adapter.JsonResponseAdapter;
import br.com.ezschedule.apischedule.model.Condominium;
import br.com.ezschedule.apischedule.model.DtoClasses.CondominiumInformationDto;
import br.com.ezschedule.apischedule.model.DtoClasses.Response.CondominiumResponse;
import br.com.ezschedule.apischedule.repository.CondominumRepository;
import br.com.ezschedule.apischedule.service.CondominumService;
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

@Api(value = "Condomínio", produces = MediaType.APPLICATION_JSON_VALUE, tags = {"condominio"}, description = "requisições relacionadas ao condomínio")
@RestController
@RequestMapping("${uri.condominium}")
public class CondominumController {

    @Autowired
    private CondominumRepository condominumRepository;

    @Autowired
    private CondominumService service;

    @ApiResponse(responseCode = "204", description =
            "Não há condomínio cadastrados.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "condomínios encontrados.")
    @GetMapping
    public ResponseEntity<List<CondominiumResponse>> listar(){
        List<Condominium> condominiumList = service.listAll();

        if(!condominiumList.isEmpty()){
            return ResponseEntity.status(200).body(JsonResponseAdapter.listCondominumDTO(condominiumList));
        }
        return ResponseEntity.status(204).build();
    }

    @ApiResponse(responseCode = "201", description =
            "Condomínio cadastrado", content = @Content(schema = @Schema(hidden = true)))
    @PostMapping
    public ResponseEntity<Condominium> addCondominum(@RequestBody @Valid Condominium c){
        condominumRepository.save(c);

        return ResponseEntity.status(201).build();
    }

    @GetMapping("/settings")
    public ResponseEntity<CondominiumInformationDto> settingsCondominiumInformation(@RequestParam Integer id) {
        Integer amountTenants = condominumRepository.amountTenantsCondominium(id);
        Integer amountApartments = condominumRepository.amountApartmentsCondominium(id);
        Integer amountSaloons = condominumRepository.amountSaloonsCondominium(id);

        return ResponseEntity.status(200).body(new CondominiumInformationDto(
                amountTenants, amountApartments, amountSaloons
        ));
    }
}

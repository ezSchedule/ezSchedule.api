package br.com.ezschedule.apischedule.controller;

import br.com.ezschedule.apischedule.model.DtoClasses.Response.ScheduleResponse;
import br.com.ezschedule.apischedule.model.DtoClasses.UpdateResponse.UpdateScheduleForm;
import br.com.ezschedule.apischedule.model.Schedule;
import br.com.ezschedule.apischedule.service.ScheduleService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Agendamentos", produces = MediaType.APPLICATION_JSON_VALUE, tags = {"agendamentos"}, description = "requisições relacionadas a agendamentos")
@RestController
@RequestMapping("${uri.schedule}")
public class ScheduleController {

    @Autowired
    ScheduleService service;

    @ApiResponse(responseCode = "204", description = "Não há agendamentos cadastrados.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "Agendamentos encontrados.")
    @GetMapping
    public ResponseEntity<List<ScheduleResponse>> showAllSchedules() {
        return service.findAll();
    }

    @GetMapping("/tenant/{id}")
    public ResponseEntity<List<ScheduleResponse>> showAllSchedulesByTenant(@PathVariable int id) {
        return service.findAllSchedulesByTenant(id);
    }

    @ApiResponse(responseCode = "404", description = "Nenhuma informação encontrada.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "Informações encontradas.")
    @GetMapping("/findSchedule/{idCondominium}")
    public ResponseEntity<Object> findScheduleByMonth(@PathVariable int idCondominium) {
        return service.findScheduleByMonth(idCondominium);
    }

    @ApiResponse(responseCode = "404", description =
            "Não foi encontrado agendamento.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "agendamento encontrado.")
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponse> showAScheduleById(@PathVariable int id) {
        return service.findById(id);
    }
    @GetMapping("/condominium/{id}")
    public ResponseEntity<List<ScheduleResponse>> showScheduleByCondominiumId(@PathVariable int id){
        return service.findByCondominiumId(id);
    }

    @ApiResponse(responseCode = "201", description =
            "Agendamento cadastrado", content = @Content(schema = @Schema(hidden = true)))
    @PostMapping
    public ResponseEntity<ScheduleResponse> newSchedule(@RequestBody @Valid Schedule s) {
        return service.add(s);
    }

    @ApiResponse(responseCode = "404", description =
            "Não há agendamentos cadastrados.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "Agendamento atualizado com sucesso.")
    @PutMapping("/update/{id}")
    public ResponseEntity<ScheduleResponse> updateScheduleById(@RequestBody @Valid UpdateScheduleForm newSchedule, @PathVariable int id) {
        return service.update(newSchedule, id);
    }

    @ApiResponse(responseCode = "404", description =
            "Não há agendamentos cadastrados.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "agendamento deletado com sucesso.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteScheduleById(@PathVariable int id) {
        return service.delete(id);
    }

    @GetMapping("/type")
    public ResponseEntity<List<ScheduleResponse>> getAllCanceledSchedules() {
        return service.getAllCanceledSchedules();
    }

}

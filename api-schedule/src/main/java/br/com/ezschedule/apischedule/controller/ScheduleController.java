package br.com.ezschedule.apischedule.controller;

import br.com.ezschedule.apischedule.adapter.JsonResponseAdapter;
import br.com.ezschedule.apischedule.model.DtoClasses.ScheduleDTO;
import br.com.ezschedule.apischedule.model.DtoClasses.UpdateResponse.UpdateScheduleForm;
import br.com.ezschedule.apischedule.model.Schedule;
import br.com.ezschedule.apischedule.repository.ScheduleRepository;
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

@Api(value = "Agendamentos", produces = MediaType.APPLICATION_JSON_VALUE, tags = {"agendamentos"}, description = "requisições relacionadas a agendamentos")
@RestController
@RequestMapping("${uri.schedule}")
public class ScheduleController {

    @Autowired
    ScheduleRepository scheduleRepository;

    @ApiResponse(responseCode = "204", description =
            "Não há agendamentos cadastrados.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "Agendamentos encontrados.")
    @GetMapping
    public ResponseEntity<List<ScheduleDTO>> showAllSchedules(){
        List<Schedule> allSchedules = scheduleRepository.findAll();
        if(allSchedules.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(JsonResponseAdapter.listScheduleDTO(allSchedules));
    }

    @ApiResponse(responseCode = "404", description =
            "Não foi encontrado agendamento.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "agendamento encontrado.")
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDTO> showAScheduleById(@PathVariable int id){
        Optional<Schedule> schedule = scheduleRepository.findById(id);
        if(schedule.isEmpty()){
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(JsonResponseAdapter.scheduleDTO(schedule.get()));
    }

    @ApiResponse(responseCode = "201", description =
            "Agendamento cadastrado", content = @Content(schema = @Schema(hidden = true)))
    @PostMapping
    public ResponseEntity<ScheduleDTO> newSchedule(@RequestBody @Valid Schedule s){
        scheduleRepository.save(s);
        return ResponseEntity.status(200).body(JsonResponseAdapter.scheduleDTO(s));
    }

    @ApiResponse(responseCode = "404", description =
            "Não há agendamentos cadastrados.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "Agendamento atualizado com sucesso.")
    @PutMapping("/{id}")
    public ResponseEntity<Schedule> updateScheduleById(@RequestBody @Valid UpdateScheduleForm newSchedule, @PathVariable int id){
        Optional<Schedule> oldSchedule = scheduleRepository.findById(id);

        if(oldSchedule.isPresent()){
            Schedule updatedSchedule = JsonResponseAdapter.updateScheduleDTO(
                    newSchedule,
                    id,
                    oldSchedule.get().getSaloon(),
                    oldSchedule.get().getTenant()
            );

            scheduleRepository.save(updatedSchedule);
            return ResponseEntity.status(200).body(updatedSchedule);
        }
        return ResponseEntity.status(404).build();
    }

    @ApiResponse(responseCode = "404", description =
            "Não há agendamentos cadastrados.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "agendamento deletado com sucesso.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteScheduleById(@PathVariable int id){
        if(scheduleRepository.existsById(id)){
            scheduleRepository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }
}

package br.com.ezschedule.apischedule.controller;

import br.com.ezschedule.apischedule.adapter.JsonResponseAdapter;
import br.com.ezschedule.apischedule.model.DtoClasses.Response.ScheduleResponse;
import br.com.ezschedule.apischedule.model.DtoClasses.ScheduleDTO;
import br.com.ezschedule.apischedule.model.DtoClasses.UpdateResponse.UpdateScheduleForm;
import br.com.ezschedule.apischedule.model.DtoClasses.InfoDate;
import br.com.ezschedule.apischedule.model.Schedule;
import br.com.ezschedule.apischedule.repository.ScheduleRepository;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.*;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Api(value = "Agendamentos", produces = MediaType.APPLICATION_JSON_VALUE, tags = {"agendamentos"}, description = "requisições relacionadas a agendamentos")
@RestController
@RequestMapping("${uri.schedule}")
public class ScheduleController {

    @Autowired
    ScheduleRepository scheduleRepository;



    @ApiResponse(responseCode = "404", description =
            "Nenhuma informação encontrada.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "Informações encontradas.")
    @GetMapping("/findSchedule/{idCondominium}")
    public ResponseEntity<Object> findScheduleByMonth(@PathVariable int idCondominium){
        List<Schedule> listSchedule = scheduleRepository.findAll();

        if(listSchedule.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        List<Object> listMonth = new ArrayList<>();

        LocalDate dateOneJaneiro = LocalDate.of(LocalDate.now().getYear(), 1,1);
        LocalDate dateTwoJaneiro = LocalDate.of( LocalDate.now().getYear(), 1,31);
        listMonth.add(dateOneJaneiro);
        listMonth.add(dateTwoJaneiro);

        LocalDate dateOneFevereiro = LocalDate.of(LocalDate.now().getYear(), 2,1);
        LocalDate dateTwoFevereiro = LocalDate.of( LocalDate.now().getYear(), 2,28);
        listMonth.add(dateOneFevereiro);
        listMonth.add(dateTwoFevereiro);

        LocalDate dateOneMarco = LocalDate.of(LocalDate.now().getYear(), 3,1);
        LocalDate dateTwoMarco = LocalDate.of( LocalDate.now().getYear(), 3,31);
        listMonth.add(dateOneMarco);
        listMonth.add(dateTwoMarco);

        LocalDate dateOneAbril = LocalDate.of(LocalDate.now().getYear(), 4,1);
        LocalDate dateTwoAbril = LocalDate.of( LocalDate.now().getYear(), 4,30);
        listMonth.add(dateOneAbril);
        listMonth.add(dateTwoAbril);

        LocalDate dateOneMaio = LocalDate.of(LocalDate.now().getYear(), 5,1);
        LocalDate dateTwoMaio= LocalDate.of( LocalDate.now().getYear(), 5,31);
        listMonth.add(dateOneMaio);
        listMonth.add(dateTwoMaio);

        LocalDate dateOneJunho = LocalDate.of(LocalDate.now().getYear(), 6,1);
        LocalDate dateTwoJunho = LocalDate.of( LocalDate.now().getYear(), 6,30);
        listMonth.add(dateOneJunho);
        listMonth.add(dateTwoJunho);

        LocalDate dateOneJulho = LocalDate.of(LocalDate.now().getYear(), 7,1);
        LocalDate dateTwoJulho = LocalDate.of( LocalDate.now().getYear(), 7,31);
        listMonth.add(dateOneJulho);
        listMonth.add(dateTwoJulho);

        LocalDate dateOneAgosto = LocalDate.of(LocalDate.now().getYear(), 8,1);
        LocalDate dateTwoAgosto = LocalDate.of( LocalDate.now().getYear(), 8,31);
        listMonth.add(dateOneAgosto);
        listMonth.add(dateTwoAgosto);

        LocalDate dateOneSetembro = LocalDate.of( LocalDate.now().getYear(), 9,1);
        LocalDate dateTwoSetembro = LocalDate.of( LocalDate.now().getYear(), 9,30);
        listMonth.add(dateOneSetembro);
        listMonth.add(dateTwoSetembro);

        LocalDate dateOneOutubro = LocalDate.of(LocalDate.now().getYear(), 10,1);
        LocalDate dateTwoOutubro = LocalDate.of( LocalDate.now().getYear(), 10,31);
        listMonth.add(dateOneOutubro);
        listMonth.add(dateTwoOutubro);

        LocalDate dateOneNovembro = LocalDate.of(LocalDate.now().getYear(), 11,1);
        LocalDate dateTwoNovembro = LocalDate.of( LocalDate.now().getYear(), 11,30);
        listMonth.add(dateOneNovembro);
        listMonth.add(dateTwoNovembro);

        LocalDate dateOneDezembro = LocalDate.of(LocalDate.now().getYear(), 12,1);
        LocalDate dateTwoDezembro = LocalDate.of( LocalDate.now().getYear(), 12,30);
        listMonth.add(dateOneDezembro);
        listMonth.add(dateTwoDezembro);

        List<InfoDate> informationResultList= new ArrayList<>();

        for(int i=0; i < listMonth.size(); i++){
            if(i % 2 == 0){

                LocalDate selectedDateOneMonth = (LocalDate) listMonth.get(i);
                LocalDate selectedDateTwoMonth = (LocalDate) listMonth.get(i+1);

                LocalDateTime startDateTime = selectedDateOneMonth.atStartOfDay();
                LocalDateTime endDateTime = selectedDateTwoMonth.atTime(LocalTime.MAX);

                Month month = selectedDateOneMonth.getMonth();

                Locale locale = Locale.forLanguageTag("pt-br");
                String monthStringExtended = month.getDisplayName(TextStyle.FULL, locale);
                monthStringExtended = monthStringExtended.substring(0,1).toUpperCase().concat(monthStringExtended.substring(1));

                Integer totalGuestsByMonth = scheduleRepository.totalGuestsByMonth(startDateTime, endDateTime, idCondominium);
                Integer totalEventsByMonth = scheduleRepository.countEventsByMonth(startDateTime, endDateTime, idCondominium);

                informationResultList.add(new InfoDate(monthStringExtended, totalGuestsByMonth, totalEventsByMonth));
            }

        }
        return ResponseEntity.status(200).body(informationResultList);
    }

    @ApiResponse(responseCode = "204", description =
            "Não há agendamentos cadastrados.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "Agendamentos encontrados.")
    @GetMapping
    public ResponseEntity<List<ScheduleResponse>> showAllSchedules(){
        List<Schedule> allSchedules = scheduleRepository.findAll();
        if(allSchedules.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(JsonResponseAdapter.listScheduleResponse(allSchedules));
    }

    @ApiResponse(responseCode = "404", description =
            "Não foi encontrado agendamento.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "agendamento encontrado.")
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponse> showAScheduleById(@PathVariable int id){
        Optional<Schedule> schedule = scheduleRepository.findById(id);
        if(schedule.isEmpty()){
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(JsonResponseAdapter.scheduleResponse(schedule.get()));
    }

    @ApiResponse(responseCode = "201", description =
            "Agendamento cadastrado", content = @Content(schema = @Schema(hidden = true)))
    @PostMapping
    public ResponseEntity<ScheduleResponse> newSchedule(@RequestBody @Valid Schedule s){
        scheduleRepository.save(s);
        return ResponseEntity.status(200).body(JsonResponseAdapter.scheduleResponse(s));
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

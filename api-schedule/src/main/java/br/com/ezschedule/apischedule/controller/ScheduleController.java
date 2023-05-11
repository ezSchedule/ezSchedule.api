package br.com.ezschedule.apischedule.controller;

import br.com.ezschedule.apischedule.adapter.JsonResponseAdapter;
import br.com.ezschedule.apischedule.model.DtoClasses.InfoDate;
import br.com.ezschedule.apischedule.model.DtoClasses.ScheduleDTO;
import br.com.ezschedule.apischedule.model.DtoClasses.UpdateScheduleForm;
import br.com.ezschedule.apischedule.model.Schedule;
import br.com.ezschedule.apischedule.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("${uri.schedule}")
public class ScheduleController {

    @Autowired
    ScheduleRepository scheduleRepository;



    @GetMapping("/schedule/month/")
    public ResponseEntity<Object> findScheduleByMonth(LocalDate dateOne, LocalDate dateTwo){
        List<Schedule> listSchedule = scheduleRepository.findAll();
        if(listSchedule.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        Month month = dateOne.getMonth();
        Year year = Year.of(dateOne.getYear());

        //Transforma pra string
//        String monthString = month.toString();
//        String yearString = year.toString();

        //Busca o mês correspondente com o número - Para passa o mês
        Locale locale = Locale.forLanguageTag("pt-br");
        String monthStringExtended = month.getDisplayName(TextStyle.FULL, locale);

        //Pesquisar totais
        //-------------------------Integer totalEventsByMonth = scheduleRepository.countEventsByMonth(dataOne, dataTwo) totalEventsByMonth;
        Integer totalGuestsByMonth = scheduleRepository.totalGuestsByMonth(dateOne, dateTwo);

        InfoDate infoDate = new InfoDate(monthStringExtended, totalGuestsByMonth);

        return ResponseEntity.status(200).body(infoDate);
    }

    @GetMapping
    public ResponseEntity<List<Schedule>> showAllSchedules(){
        List<Schedule> allSchedules = scheduleRepository.findAll();
        if(allSchedules.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(allSchedules);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Schedule> showAScheduleById(@PathVariable int id){
        Optional<Schedule> schedule = scheduleRepository.findById(id);
        if(schedule.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(schedule.get());
    }

    @PostMapping
    public ResponseEntity<Schedule> newSchedule(@RequestBody @Valid Schedule s){
        scheduleRepository.save(s);
        return ResponseEntity.status(200).body(s);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Schedule> updateScheduleById(@RequestBody @Valid UpdateScheduleForm newSchedule, @PathVariable int id){
//        Optional<Schedule> oldSchedule = scheduleRepository.findById(id);
//
//        if(oldSchedule.isPresent()){
//            Schedule updatedSchedule = JsonResponseAdapter.scheduleDTO(
//                    newSchedule,
//                    id,
//                    oldSchedule.get().getSaloon(),
//                    oldSchedule.get().getTenant()
//            );
//
//            scheduleRepository.save(updatedSchedule);
//            return ResponseEntity.status(200).body(updatedSchedule);
//        }
//        return ResponseEntity.status(404).build();
//    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteScheduleById(@PathVariable int id){
        if(scheduleRepository.existsById(id)){
            scheduleRepository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }



}

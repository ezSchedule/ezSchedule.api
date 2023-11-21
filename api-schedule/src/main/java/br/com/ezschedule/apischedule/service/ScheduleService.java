package br.com.ezschedule.apischedule.service;

import br.com.ezschedule.apischedule.adapter.JsonResponseAdapter;
import br.com.ezschedule.apischedule.model.DtoClasses.InfoDate;
import br.com.ezschedule.apischedule.model.DtoClasses.InfoDateV2;
import br.com.ezschedule.apischedule.model.DtoClasses.Response.ScheduleResponse;
import br.com.ezschedule.apischedule.model.DtoClasses.UpdateResponse.UpdateScheduleForm;
import br.com.ezschedule.apischedule.model.Schedule;
import br.com.ezschedule.apischedule.repository.CondominumRepository;
import br.com.ezschedule.apischedule.repository.ScheduleRepository;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    CondominumRepository condominumRepository;

    public ResponseEntity<Object> findScheduleByMonth(@PathVariable int idCondominium) {
        List<Schedule> listSchedule = scheduleRepository.findAllSchedules();

        if (listSchedule.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        List<Object> listMonth = new ArrayList<>();

        LocalDate dateOneJaneiro = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        LocalDate dateTwoJaneiro = LocalDate.of(LocalDate.now().getYear(), 1, 31);
        listMonth.add(dateOneJaneiro);
        listMonth.add(dateTwoJaneiro);

        LocalDate dateOneFevereiro = LocalDate.of(LocalDate.now().getYear(), 2, 1);
        LocalDate dateTwoFevereiro = LocalDate.of(LocalDate.now().getYear(), 2, 28);
        listMonth.add(dateOneFevereiro);
        listMonth.add(dateTwoFevereiro);

        LocalDate dateOneMarco = LocalDate.of(LocalDate.now().getYear(), 3, 1);
        LocalDate dateTwoMarco = LocalDate.of(LocalDate.now().getYear(), 3, 31);
        listMonth.add(dateOneMarco);
        listMonth.add(dateTwoMarco);

        LocalDate dateOneAbril = LocalDate.of(LocalDate.now().getYear(), 4, 1);
        LocalDate dateTwoAbril = LocalDate.of(LocalDate.now().getYear(), 4, 30);
        listMonth.add(dateOneAbril);
        listMonth.add(dateTwoAbril);

        LocalDate dateOneMaio = LocalDate.of(LocalDate.now().getYear(), 5, 1);
        LocalDate dateTwoMaio = LocalDate.of(LocalDate.now().getYear(), 5, 31);
        listMonth.add(dateOneMaio);
        listMonth.add(dateTwoMaio);

        LocalDate dateOneJunho = LocalDate.of(LocalDate.now().getYear(), 6, 1);
        LocalDate dateTwoJunho = LocalDate.of(LocalDate.now().getYear(), 6, 30);
        listMonth.add(dateOneJunho);
        listMonth.add(dateTwoJunho);

        LocalDate dateOneJulho = LocalDate.of(LocalDate.now().getYear(), 7, 1);
        LocalDate dateTwoJulho = LocalDate.of(LocalDate.now().getYear(), 7, 31);
        listMonth.add(dateOneJulho);
        listMonth.add(dateTwoJulho);

        LocalDate dateOneAgosto = LocalDate.of(LocalDate.now().getYear(), 8, 1);
        LocalDate dateTwoAgosto = LocalDate.of(LocalDate.now().getYear(), 8, 31);
        listMonth.add(dateOneAgosto);
        listMonth.add(dateTwoAgosto);

        LocalDate dateOneSetembro = LocalDate.of(LocalDate.now().getYear(), 9, 1);
        LocalDate dateTwoSetembro = LocalDate.of(LocalDate.now().getYear(), 9, 30);
        listMonth.add(dateOneSetembro);
        listMonth.add(dateTwoSetembro);

        LocalDate dateOneOutubro = LocalDate.of(LocalDate.now().getYear(), 10, 1);
        LocalDate dateTwoOutubro = LocalDate.of(LocalDate.now().getYear(), 10, 31);
        listMonth.add(dateOneOutubro);
        listMonth.add(dateTwoOutubro);

        LocalDate dateOneNovembro = LocalDate.of(LocalDate.now().getYear(), 11, 1);
        LocalDate dateTwoNovembro = LocalDate.of(LocalDate.now().getYear(), 11, 30);
        listMonth.add(dateOneNovembro);
        listMonth.add(dateTwoNovembro);

        LocalDate dateOneDezembro = LocalDate.of(LocalDate.now().getYear(), 12, 1);
        LocalDate dateTwoDezembro = LocalDate.of(LocalDate.now().getYear(), 12, 30);
        listMonth.add(dateOneDezembro);
        listMonth.add(dateTwoDezembro);

        List<InfoDate> informationResultList = new ArrayList<>();

        for (int i = 0; i < listMonth.size(); i++) {
            if (i % 2 == 0) {

                LocalDate selectedDateOneMonth = (LocalDate) listMonth.get(i);
                LocalDate selectedDateTwoMonth = (LocalDate) listMonth.get(i + 1);

                LocalDateTime startDateTime = selectedDateOneMonth.atStartOfDay();
                LocalDateTime endDateTime = selectedDateTwoMonth.atTime(LocalTime.MAX);

                Month month = selectedDateOneMonth.getMonth();

                Locale locale = Locale.forLanguageTag("pt-br");
                String monthStringExtended = month.getDisplayName(TextStyle.FULL, locale);
                monthStringExtended = monthStringExtended.substring(0, 1).toUpperCase().concat(monthStringExtended.substring(1));

                Integer totalGuestsByMonth = scheduleRepository.totalGuestsByMonth(startDateTime, endDateTime, idCondominium);
                if (totalGuestsByMonth == null) {
                    totalGuestsByMonth = 0;
                }
                Integer totalEventsByMonth = scheduleRepository.countEventsByMonth(startDateTime, endDateTime, idCondominium);

                informationResultList.add(new InfoDate(monthStringExtended, totalGuestsByMonth, totalEventsByMonth));
            }

        }
        return ResponseEntity.status(200).body(informationResultList);
    }

    public ResponseEntity<List<InfoDateV2>> findAllSchedulesData(int idCondominium) {
        List<Schedule> listSchedule = scheduleRepository.findByCondominiumId(idCondominium);

        if (listSchedule.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        List<InfoDateV2> listInfoDate = new ArrayList<>();

        int year = LocalDate.now().getYear();
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 1, 31);

        for (int i = 0; i < 12; i++) {
            LocalDateTime startDateTime = startDate.atStartOfDay().plusMonths(i);
            LocalDateTime endDateTime = endDate.atStartOfDay().plusMonths(i);
            Integer totalGuestByMonth = scheduleRepository.totalGuestsByMonth(startDateTime, endDateTime, idCondominium);
            if (totalGuestByMonth == null) {
                totalGuestByMonth = 0;
            }
            Integer totalEventsByMonth = scheduleRepository.countEventsByMonth(startDateTime, endDateTime, idCondominium);
            listInfoDate.add(new InfoDateV2(i + 1, totalGuestByMonth, totalEventsByMonth));
        }

        return ResponseEntity.status(200).body(listInfoDate);

    }


    public ResponseEntity<List<ScheduleResponse>> findAll() {
        List<Schedule> allSchedules = scheduleRepository.findAllSchedules();
        if (allSchedules.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(JsonResponseAdapter.listScheduleResponse(allSchedules));
    }

    public ResponseEntity<List<ScheduleResponse>> findAllSchedulesByTenant(int id) {
        List<Schedule> allSchedules = scheduleRepository.findAllSchedulesByTenant(id);
        if (allSchedules.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(JsonResponseAdapter.listScheduleResponse(allSchedules));
    }

    public ResponseEntity<List<ScheduleResponse>> findByCondominiumId(@PathVariable int id) {
        if (condominumRepository.existsById(id)) {
            List<Schedule> schedule = scheduleRepository.findByCondominiumId(id);
            if (!schedule.isEmpty()) {
                return ResponseEntity.status(200).body(JsonResponseAdapter.listScheduleResponse(schedule));
            }
        }
        return ResponseEntity.status(404).build();
    }

    public ResponseEntity<ScheduleResponse> findById(@PathVariable int id) {
        Optional<Schedule> schedule = scheduleRepository.findById(id);
        if (schedule.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(JsonResponseAdapter.scheduleResponse(schedule.get()));
    }

    public ResponseEntity<Map<String, Object>> add(Schedule s) {
        scheduleRepository.save(s);
        Map<String, Object> data = JsonResponseAdapter.reportHash(s);

        return ResponseEntity.status(201).body(data);
    }

    public ResponseEntity<ScheduleResponse> update(UpdateScheduleForm newSchedule, int id) {
        Optional<Schedule> oldSchedule = scheduleRepository.findById(id);

        if (oldSchedule.isPresent()) {
            Schedule updatedSchedule = JsonResponseAdapter.updateScheduleDTO(
                    newSchedule,
                    id,
                    oldSchedule.get().getSaloon(),
                    oldSchedule.get().getTenant()
            );

            scheduleRepository.save(updatedSchedule);
            return ResponseEntity.status(200).body(JsonResponseAdapter.scheduleResponse(updatedSchedule));
        }
        return ResponseEntity.status(404).build();
    }

    @Transactional
    public ResponseEntity<Void> delete(int id) {
        if (scheduleRepository.existsById(id)) {

            if (scheduleRepository.isThisScheduleCanceled(id) == 1) {
                scheduleRepository.deleteById(id);
                return ResponseEntity.status(204).build();
            }
            return ResponseEntity.status(418).build();
        }
        return ResponseEntity.status(404).build();
    }

    public ResponseEntity<List<ScheduleResponse>> getAllCanceledSchedules() {
        List<Schedule> scheduleList = scheduleRepository.findAllCanceledSchedules();
        if (scheduleList.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(JsonResponseAdapter.listScheduleResponse(scheduleList));
    }


}

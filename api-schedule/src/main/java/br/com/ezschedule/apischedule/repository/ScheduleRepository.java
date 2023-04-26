package br.com.ezschedule.apischedule.repository;

import br.com.ezschedule.apischedule.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

public interface ScheduleRepository extends JpaRepository<Schedule,Integer> {

    Schedule findByDate(LocalDate date);

    @Query("Select count(*) from Schedule where year = (?) group by month = (?)")
   Integer countEventsByMonth(String month, String year);

    @Query("Select count(total_number_guests where year = (?) group by month = (?)")
    Integer totalGuestsByMonth(String month, String year);
}

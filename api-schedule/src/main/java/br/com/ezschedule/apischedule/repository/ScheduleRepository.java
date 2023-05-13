package br.com.ezschedule.apischedule.repository;

import br.com.ezschedule.apischedule.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

public interface ScheduleRepository extends JpaRepository<Schedule,Integer> {

    Schedule findByDateEvent(LocalDate date);


//    @Query(value = "SELECT COUNT(*) FROM Schedule WHERE year = :year GROUP BY year, month HAVING month = :month", nativeQuery = true)
//    Integer countEventsByMonth(int year, int month);

    @Query(value = "select sum(total_number_guests) from schedule where date_event between :dateOne and :dateTwo;",nativeQuery = true)
    Integer totalGuestsByMonth(LocalDate dateOne, LocalDate dateTwo);
}

package br.com.ezschedule.apischedule.repository;

import br.com.ezschedule.apischedule.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ScheduleRepository extends JpaRepository<Schedule,Integer> {

    @Query("SELECT COUNT(s.totalNumberGuests) " +
            "FROM Schedule s " +
            "JOIN s.tenant t " +
            "WHERE s.dateEvent >= :selectedDateOneMonth AND s.dateEvent <= :selectedDateTwoMonth AND t.condominium.id = :id")
        Integer totalGuestsByMonth(@Param("selectedDateOneMonth") LocalDateTime selectedDateOneMonth,
                                   @Param("selectedDateTwoMonth") LocalDateTime selectedDateTwoMonth,
                                   @Param("id") int id);

        @Query("SELECT COUNT(s.totalNumberGuests) " +
                "FROM Schedule s " +
                "JOIN s.tenant t " +
                "WHERE s.dateEvent >= :selectedDateOneMonth AND s.dateEvent <= :selectedDateTwoMonth AND t.condominium.id = :id")
        Integer countEventsByMonth(@Param("selectedDateOneMonth") LocalDateTime selectedDateOneMonth,
                                   @Param("selectedDateTwoMonth") LocalDateTime selectedDateTwoMonth,
                                   @Param("id") int id);

    }

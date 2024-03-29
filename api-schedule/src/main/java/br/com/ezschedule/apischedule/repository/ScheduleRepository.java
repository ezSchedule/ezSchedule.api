package br.com.ezschedule.apischedule.repository;

import br.com.ezschedule.apischedule.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    @Query("Select s from Schedule s where s.isCanceled = 0")
    List<Schedule> findAllSchedules();

    @Query("Select s from Schedule s where s.isCanceled = 1")
    List<Schedule> findAllCanceledSchedules();

    @Query("Select s from Schedule s where s.tenant.condominium.id = :id")
    List<Schedule> findByCondominiumId(int id);

    @Query("SELECT SUM(s.totalNumberGuests) " +
            "FROM Schedule s " +
            "JOIN s.tenant t " +
            "WHERE s.dateEvent >= :selectedDateOneMonth AND s.dateEvent <= :selectedDateTwoMonth AND t.condominium.id = :id AND s.isCanceled = 0")
    Integer totalGuestsByMonth(@Param("selectedDateOneMonth") LocalDateTime selectedDateOneMonth,
                               @Param("selectedDateTwoMonth") LocalDateTime selectedDateTwoMonth,
                               @Param("id") int id);

    @Query("SELECT COUNT(s.id)" +
            "FROM Schedule s " +
            "JOIN s.tenant t " +
            "WHERE s.dateEvent >= :selectedDateOneMonth AND s.dateEvent <= :selectedDateTwoMonth AND t.condominium.id = :id AND s.isCanceled = 0")
    Integer countEventsByMonth(@Param("selectedDateOneMonth") LocalDateTime selectedDateOneMonth,
                               @Param("selectedDateTwoMonth") LocalDateTime selectedDateTwoMonth,
                               @Param("id") int id);

    @Query("Select s from Schedule s where s.tenant.id = :id AND s.isCanceled = 0")
    List<Schedule> findAllSchedulesByTenant(int id);


    @Query("Select count(s.id) from Schedule s where s.isCanceled = 0 AND s.id = :id")
    Integer isThisScheduleCanceled(int id);

    @Query("select count(s.id) FROM Schedule s WHERE s.dateEvent = :dateEvent ")
    Integer haveBeenScheduled(LocalDateTime dateEvent);


}

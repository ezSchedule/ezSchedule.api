package br.com.ezschedule.apischedule.repository;

import br.com.ezschedule.apischedule.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule,Integer> {

    @Query("Select s from Schedule s where s.typeEvent = 'cancelado'")
    List<Schedule> findAllCanceledSchedules();

}

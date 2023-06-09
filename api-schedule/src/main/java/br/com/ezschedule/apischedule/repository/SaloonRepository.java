package br.com.ezschedule.apischedule.repository;

import br.com.ezschedule.apischedule.model.Saloon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SaloonRepository extends JpaRepository<Saloon, Integer> {

    @Query("Select s from Saloon s where s.condominium.id = :id")
    List<Saloon> findAllSaloonByCondominium(int id);


    @Query("Select count(s.id) from Saloon join Schedule s on s.saloon.id = :id where s.dateEvent = :date")
    Integer countIfDateIsScheduled(int id, LocalDateTime date);

}

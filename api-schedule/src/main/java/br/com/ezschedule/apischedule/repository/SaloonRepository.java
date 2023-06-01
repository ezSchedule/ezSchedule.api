package br.com.ezschedule.apischedule.repository;

import br.com.ezschedule.apischedule.model.Report;
import br.com.ezschedule.apischedule.model.Saloon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaloonRepository extends JpaRepository<Saloon, Integer> {

    @Query("Select s from Saloon s where s.condominium.id = :id")
    List<Saloon> findAllSaloonByCondominium(int id);

}

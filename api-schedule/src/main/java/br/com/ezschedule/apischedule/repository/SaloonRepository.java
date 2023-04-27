package br.com.ezschedule.apischedule.repository;

import br.com.ezschedule.apischedule.model.Report;
import br.com.ezschedule.apischedule.model.Saloon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaloonRepository extends JpaRepository<Saloon, Integer> {

}

package br.com.ezschedule.apischedule.repository;

import br.com.ezschedule.apischedule.model.Condominium;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CondominumRepository extends JpaRepository<Condominium,Integer> {
}

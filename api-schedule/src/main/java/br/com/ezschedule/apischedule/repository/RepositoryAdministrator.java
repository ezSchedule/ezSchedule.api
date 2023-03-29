package br.com.ezschedule.apischedule.repository;

import br.com.ezschedule.apischedule.model.Administrator;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryAdministrator extends CrudRepository<Administrator, Integer> {

}

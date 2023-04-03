package br.com.ezschedule.apischedule.repository;

import br.com.ezschedule.apischedule.model.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryClient extends CrudRepository<Client, Integer> {
    Boolean existsByEmail(String email);
}

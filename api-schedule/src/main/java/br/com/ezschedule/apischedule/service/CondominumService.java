package br.com.ezschedule.apischedule.service;

import br.com.ezschedule.apischedule.model.Condominium;
import br.com.ezschedule.apischedule.model.DtoClasses.Response.CondominiumResponse;
import br.com.ezschedule.apischedule.repository.CondominumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CondominumService {

    @Autowired
    private CondominumRepository condominumRepository;

    public List<Condominium> listAll() {
        return condominumRepository.findAll();
    }
}

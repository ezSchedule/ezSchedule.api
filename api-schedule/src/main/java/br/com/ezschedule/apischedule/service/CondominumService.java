package br.com.ezschedule.apischedule.service;

import br.com.ezschedule.apischedule.model.Condominium;
import br.com.ezschedule.apischedule.model.DtoClasses.CondominiumInformationDto;
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

    public Condominium saveCondominium(Condominium c) {
        return condominumRepository.save(c);
    }

    public CondominiumInformationDto settingsCondominium(Integer id) {

        Integer amountTenants = condominumRepository.amountTenantsCondominium(id);
        Integer amountApartments = condominumRepository.amountApartmentsCondominium(id);
        Integer amountSaloons = condominumRepository.amountSaloonsCondominium(id);

        CondominiumInformationDto condominiumInformationDto = new CondominiumInformationDto(
                amountTenants, amountApartments, amountSaloons
        );

        return condominiumInformationDto;

    }

}

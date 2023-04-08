package br.com.ezschedule.apischedule.service;

import br.com.ezschedule.apischedule.data.AdministratorDetailData;
import br.com.ezschedule.apischedule.model.Administrator;
import br.com.ezschedule.apischedule.repository.AdministratorRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DetailAdministratorServiceImpl implements UserDetailsService {

    private final AdministratorRepository administratorRepository;

    public DetailAdministratorServiceImpl(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Administrator> administrator = administratorRepository.findByEmail(email);

        if (administrator.isEmpty()){
            throw  new UsernameNotFoundException("User ["+email+"] not found");
        }
        return new AdministratorDetailData(administrator);
    }
}

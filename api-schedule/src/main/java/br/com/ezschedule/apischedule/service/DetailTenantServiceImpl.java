package br.com.ezschedule.apischedule.service;

import br.com.ezschedule.apischedule.data.AdministratorDetailData;
import br.com.ezschedule.apischedule.data.TenantDetailData;
import br.com.ezschedule.apischedule.model.Tenant;
import br.com.ezschedule.apischedule.repository.AdministratorRepository;
import br.com.ezschedule.apischedule.repository.TenantRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class DetailTenantServiceImpl implements UserDetailsService {

    private final TenantRepository tenantRepository;

    public DetailTenantServiceImpl(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Tenant> tenant = this.tenantRepository.findByEmail(email);

        if (tenant.isEmpty()){
            throw new UsernameNotFoundException("User ["+email+"] not found");
        }
        return new TenantDetailData(tenant);
    }
}

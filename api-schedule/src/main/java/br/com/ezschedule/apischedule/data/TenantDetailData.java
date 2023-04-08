package br.com.ezschedule.apischedule.data;

import br.com.ezschedule.apischedule.model.Tenant;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class TenantDetailData implements UserDetails {

    final private Optional<Tenant> tenant;

    public TenantDetailData(Optional<Tenant> tenant) {
        this.tenant = tenant;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return tenant.orElse(new Tenant()).getPassword();
    }

    @Override
    public String getUsername() {
        return tenant.orElse(new Tenant()).getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

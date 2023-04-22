package br.com.ezschedule.apischedule.model.DtoClasses;

import br.com.ezschedule.apischedule.model.Tenant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ServiceDTO {

    @NotBlank
    private String nomeServico;

    @NotNull
    private TenantResponse tenant;

    public ServiceDTO(String nomeServico, TenantResponse tenant) {
        this.nomeServico = nomeServico;
        this.tenant = tenant;
    }

    public ServiceDTO() {
    }

    public String getNomeServico() {
        return nomeServico;
    }

    public void setNomeServico(String nomeServico) {
        this.nomeServico = nomeServico;
    }

    public TenantResponse getTenant() {
        return tenant;
    }

    public void setTenant(TenantResponse tenant) {
        this.tenant = tenant;
    }
}

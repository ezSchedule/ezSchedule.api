package br.com.ezschedule.apischedule.model;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Service {
    @NotBlank
    private String nomeServico;

    @NotNull
    private Tenant tenant;

    public Service(String nomeServico, Tenant tenant) {
        this.nomeServico = nomeServico;
        this.tenant = tenant;
    }

    public Service(){

    }

    public String getNomeServico() {
        return nomeServico;
    }

    public void setNomeServico(String nomeServico) {
        this.nomeServico = nomeServico;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    @Override
    public String toString() {
        return "Service{" +
                "nomeServico='" + nomeServico + '\'' +
                ", tenant=" + tenant +
                '}';
    }
}

package br.com.ezschedule.apischedule.model;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Service {
    @NotBlank
    private String serviceName;

    @NotNull
    private Tenant tenant;

    public Service(String serviceName, Tenant tenant) {
        this.serviceName = serviceName;
        this.tenant = tenant;
    }

    public Service(){

    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
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
                "nomeServico='" + serviceName + '\'' +
                ", tenant=" + tenant +
                '}';
    }
}

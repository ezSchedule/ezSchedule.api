package br.com.ezschedule.apischedule.model.DtoClasses;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ServiceDTO {

    @NotBlank
    private String serviceName;

    @NotNull
    private TenantDTO tenant;

    public ServiceDTO(String serviceName, TenantDTO tenant) {
        this.serviceName = serviceName;
        this.tenant = tenant;
    }

    public ServiceDTO() {
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public TenantDTO getTenant() {
        return tenant;
    }

    public void setTenant(TenantDTO tenant) {
        this.tenant = tenant;
    }
}

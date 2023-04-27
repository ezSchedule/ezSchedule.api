package br.com.ezschedule.apischedule.model.DtoClasses;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ServiceDTO {

    @NotBlank
    private String serviceName;

    @NotNull
    private TenantResponse tenant;

    public ServiceDTO(String serviceName, TenantResponse tenant) {
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

    public TenantResponse getTenant() {
        return tenant;
    }

    public void setTenant(TenantResponse tenant) {
        this.tenant = tenant;
    }
}

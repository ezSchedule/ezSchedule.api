package br.com.ezschedule.apischedule.model.DtoClasses.Response;

import br.com.ezschedule.apischedule.model.DtoClasses.TenantDTO;

public class ServiceResponse {
    private Integer id;

    private String serviceName;

    private TenantDTO tenant;

    public ServiceResponse(Integer id, String serviceName, TenantDTO tenant) {
        this.id = id;
        this.serviceName = serviceName;
        this.tenant = tenant;
    }

    public ServiceResponse() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

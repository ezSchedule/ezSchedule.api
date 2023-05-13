package br.com.ezschedule.apischedule.model.DtoClasses;

import br.com.ezschedule.apischedule.model.Tenant;

public class ServiceDTO {

    private Integer id;

    private String serviceName;


    private TenantDTO tenant;

    public ServiceDTO(Integer id, String serviceName, TenantDTO tenant) {
        this.id = id;
        this.serviceName = serviceName;
        this.tenant = tenant;
    }

    public ServiceDTO(Integer id, String serviceName) {
        this.id = id;
        this.serviceName = serviceName;
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

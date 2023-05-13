package br.com.ezschedule.apischedule.model.DtoClasses;

import br.com.ezschedule.apischedule.model.Tenant;

public class ServiceDTO {

    private Integer id;

    private String serviceName;

    public ServiceDTO(Integer id, String serviceName) {
        this.id = id;
        this.serviceName = serviceName;
    }

    public ServiceDTO() {
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
}

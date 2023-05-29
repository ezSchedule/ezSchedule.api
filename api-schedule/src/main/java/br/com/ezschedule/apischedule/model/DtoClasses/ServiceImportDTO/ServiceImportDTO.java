package br.com.ezschedule.apischedule.model.DtoClasses.ServiceImportDTO;

public class ServiceImportDTO {
    private String serviceName;
    private String emailUsuario;

    public ServiceImportDTO(String serviceName, String emailUsuario) {
        this.serviceName = serviceName;
        this.emailUsuario = emailUsuario;
    }

    public ServiceImportDTO() {
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }
}

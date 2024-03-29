package br.com.ezschedule.apischedule.model.DtoClasses.Response;

import br.com.ezschedule.apischedule.model.DtoClasses.CondominiumDto;
import br.com.ezschedule.apischedule.model.DtoClasses.ScheduleDTO;
import br.com.ezschedule.apischedule.model.DtoClasses.ServiceDTO;

import java.util.List;

public class TenantResponse {
    private int id;
    private String email;
    private String cpf;
    private String name;
    private String residentsBlock;
    private Integer apartmentNumber;
    private String phoneNumber;
    private boolean isAuthenticated;

    private Integer subscribed;
    private Integer isAdmin;
    private List<ScheduleDTO> scheduleList;
    private CondominiumDto condominium;
    private List<ServiceDTO> services;

    private String nameBlobImage;

    public TenantResponse(int id, String email, String cpf, String name, String residentsBlock, Integer apartmentNumber, String phoneNumber, boolean isAuthenticated, Integer isAdmin, List<ScheduleDTO> scheduleList, CondominiumDto condominium, List<ServiceDTO> services, String nameBlobImage, Integer subscribed) {
        this.id = id;
        this.email = email;
        this.cpf = cpf;
        this.name = name;
        this.residentsBlock = residentsBlock;
        this.apartmentNumber = apartmentNumber;
        this.phoneNumber = phoneNumber;
        this.isAuthenticated = isAuthenticated;
        this.isAdmin = isAdmin;
        this.subscribed = subscribed;
        this.scheduleList = scheduleList;
        this.condominium = condominium;
        this.services = services;
        this.nameBlobImage = nameBlobImage;
    }

    public TenantResponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResidentsBlock() {
        return residentsBlock;
    }

    public void setResidentsBlock(String residentsBlock) {
        this.residentsBlock = residentsBlock;
    }

    public Integer getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(Integer apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Integer getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(Integer subscribed) {
        this.subscribed = subscribed;
    }

    public List<ScheduleDTO> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<ScheduleDTO> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public CondominiumDto getCondominium() {
        return condominium;
    }

    public void setCondominium(CondominiumDto condominium) {
        this.condominium = condominium;
    }

    public List<ServiceDTO> getServices() {
        return services;
    }

    public void setServices(List<ServiceDTO> services) {
        this.services = services;
    }

    public String getNameBlobImage() {
        return nameBlobImage;
    }

    public void setNameBlobImage(String nameBlobImage) {
        this.nameBlobImage = nameBlobImage;
    }
}

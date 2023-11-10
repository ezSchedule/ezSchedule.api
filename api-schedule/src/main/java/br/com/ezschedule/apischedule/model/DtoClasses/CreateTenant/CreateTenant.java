package br.com.ezschedule.apischedule.model.DtoClasses.CreateTenant;

import br.com.ezschedule.apischedule.model.Report;
import br.com.ezschedule.apischedule.model.Schedule;
import br.com.ezschedule.apischedule.model.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class CreateTenant {
    private Integer idUser;
    private String email;
    private String cpf;
    private String password;
    private String name;
    private String residentsBlock;
    private Integer apartmentNumber;
    private String phoneNumber;
    private boolean isAuthenticated;
    private Integer isAdmin = 0;
    private Integer subscribed = 0;
    private MultipartFile nameBlobImage;
    private List<Report> reportList;
    private List<Schedule> scheduleList;
    private String condominium;
    private List<Service> services;

    public CreateTenant(Integer idUser, String email, String cpf, String password, String name, String residentsBlock, Integer apartmentNumber, String phoneNumber, boolean isAuthenticated, Integer isAdmin, MultipartFile nameBlobImage, Integer subscribed, List<Report> reportList, List<Schedule> scheduleList, String condominium, List<Service> services) {
        this.idUser = idUser;
        this.email = email;
        this.cpf = cpf;
        this.password = password;
        this.name = name;
        this.residentsBlock = residentsBlock;
        this.apartmentNumber = apartmentNumber;
        this.phoneNumber = phoneNumber;
        this.isAuthenticated = isAuthenticated;
        this.isAdmin = isAdmin;
        this.nameBlobImage = nameBlobImage;
        this.subscribed = subscribed;
        this.reportList = reportList;
        this.scheduleList = scheduleList;
        this.condominium = condominium;
        this.services = services;
    }

    public CreateTenant() {
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public MultipartFile getNameBlobImage() {
        return nameBlobImage;
    }

    public void setNameBlobImage(MultipartFile nameBlobImage) {
        this.nameBlobImage = nameBlobImage;
    }

    public Integer getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(Integer subscribed) {
        this.subscribed = subscribed;
    }

    public List<Report> getReportList() {
        return reportList;
    }

    public void setReportList(List<Report> reportList) {
        this.reportList = reportList;
    }

    public List<Schedule> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public String getCondominium() {
        return condominium;
    }

    public void setCondominium(String condominium) {
        this.condominium = condominium;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}

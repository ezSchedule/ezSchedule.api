package br.com.ezschedule.apischedule.model;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Tenant  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Email
    private String email;

    private String cpf;

    private String password;

    @NotBlank
    @Size(min=3)
    private String name;

    private String residentsBlock;

    private Integer apartmentNumber;

    private String phoneNumber;

    private boolean isAuthenticated;

    private Integer isAdmin = 0;

    private String nameBlobImage;

    private Integer subscribed = 0;

    @OneToMany(mappedBy = "tenant")
    private List<Report> reportList;

    @OneToMany(mappedBy = "tenant")
    private List<Schedule> scheduleList;

    @ManyToOne
    private Condominium condominium;

    @OneToMany(mappedBy = "tenant")
    private List<Service> services;

    public Tenant(int id, String email, String cpf, String password, String name, String residentsBlock, Integer apartmentNumber, String phoneNumber,String nameBlobImage, Integer isAdmin, List<Report> reportList, List<Schedule> scheduleList, Condominium condominium, List<Service> services) {
        this.id = id;
        this.email = email;
        this.cpf = cpf;
        this.password = password;
        this.name = name;
        this.residentsBlock = residentsBlock;
        this.apartmentNumber = apartmentNumber;
        this.phoneNumber = phoneNumber;
        this.isAdmin = isAdmin;
        this.nameBlobImage = nameBlobImage;
        this.reportList = reportList;
        this.scheduleList = scheduleList;
        this.condominium = condominium;
        this.services = services;
    }


    public Tenant(String email, String cpf, String password, String name, String residentsBlock, Integer apartmentNumber, String phoneNumber,Integer subscribed, List<Report> reportList, List<Schedule> scheduleList, Condominium condominium, List<Service> services) {
        this.email = email;
        this.cpf = cpf;
        this.password = password;
        this.name = name;
        this.residentsBlock = residentsBlock;
        this.apartmentNumber = apartmentNumber;
        this.phoneNumber = phoneNumber;
        this.subscribed = subscribed;
        this.reportList = reportList;
        this.scheduleList = scheduleList;
        this.condominium = condominium;
        this.services = services;
    }
    public Tenant(String email, String cpf, String password, String name, String residentsBlock, Integer apartmentNumber, String phoneNumber,Integer subscribed, List<Report> reportList, List<Schedule> scheduleList, Condominium condominium, List<Service> services, String nameBlobImages) {
        this.email = email;
        this.cpf = cpf;
        this.password = password;
        this.name = name;
        this.residentsBlock = residentsBlock;
        this.apartmentNumber = apartmentNumber;
        this.phoneNumber = phoneNumber;
        this.subscribed = subscribed;
        this.reportList = reportList;
        this.scheduleList = scheduleList;
        this.condominium = condominium;
        this.services = services;
    }
    public Tenant(String email, String cpf, String password, String name, String residentsBlock, Integer apartmentNumber, String phoneNumber,Integer isAdmin,Integer subscribed, List<Report> reportList, List<Schedule> scheduleList, Condominium condominium, List<Service> services) {
        this.email = email;
        this.cpf = cpf;
        this.password = password;
        this.name = name;
        this.residentsBlock = residentsBlock;
        this.apartmentNumber = apartmentNumber;
        this.phoneNumber = phoneNumber;
        this.isAdmin = isAdmin;
        this.subscribed = subscribed;
        this.reportList = reportList;
        this.scheduleList = scheduleList;
        this.condominium = condominium;
        this.services = services;
    }

    public Tenant() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getNameBlobImage() {
        return nameBlobImage;
    }

    public void setNameBlobImage(String nameBlobImage) {
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

    public Condominium getCondominium() {
        return condominium;
    }

    public void setCondominium(Condominium condominium) {
        this.condominium = condominium;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

}

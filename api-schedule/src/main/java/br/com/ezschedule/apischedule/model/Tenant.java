package br.com.ezschedule.apischedule.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@DiscriminatorValue("2")
public class Tenant extends User {

    private Integer subscribed = 0;

    @OneToMany(mappedBy = "tenant")
    private List<Report> reportList;

    @OneToMany(mappedBy = "tenant")
    private List<Schedule> scheduleList;

    @ManyToOne
    private Condominium condominium;

    @OneToMany(mappedBy = "tenant")
    private List<Service> services;

    public Tenant(int id, String email, String cpf, String password, String name, String residentsBlock, Integer apartmentNumber, String phoneNumber, Integer isAdmin, List<Report> reportList, List<Schedule> scheduleList, Condominium condominium, List<Service> services) {
        super(id, email, cpf, password, name, residentsBlock, apartmentNumber, phoneNumber, isAdmin);
        this.reportList = reportList;
        this.scheduleList = scheduleList;
        this.condominium = condominium;
        this.services = services;
    }

    public Tenant(String email, String cpf, String password, String name, String residentsBlock, Integer apartmentNumber, String phoneNumber,Integer subscribed, List<Report> reportList, List<Schedule> scheduleList, Condominium condominium, List<Service> services) {
        super(email, cpf, password, name, residentsBlock, apartmentNumber, phoneNumber);
        this.subscribed = subscribed;
        this.reportList = reportList;
        this.scheduleList = scheduleList;
        this.condominium = condominium;
        this.services = services;
    }

    public Tenant() {
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

    @Override
    public String toString() {
        return "Tenant{" +
                "report=" + reportList +
                ", schedule=" + scheduleList +
                ", condominium=" + condominium +
                '}';
    }
}

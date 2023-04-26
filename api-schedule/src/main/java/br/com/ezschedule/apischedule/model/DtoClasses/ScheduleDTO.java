package br.com.ezschedule.apischedule.model.DtoClasses;

import br.com.ezschedule.apischedule.model.Saloon;
import br.com.ezschedule.apischedule.model.Tenant;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

public class ScheduleDTO {
    private int id;
    private String nameEvent;
    private String typeEvent;
    private LocalDateTime dateEvent;
    private int totalNumberGuests;

    private List<SaloonDTO> saloon;

    private TenantResponse tenant;


    public ScheduleDTO(String nameEvent, String typeEvent, LocalDateTime dateEvent, int totalNumberGuests, List<SaloonDTO> saloon, TenantResponse tenant) {
        this.nameEvent = nameEvent;
        this.typeEvent = typeEvent;
        this.dateEvent = dateEvent;
        this.totalNumberGuests = totalNumberGuests;
        this.saloon = saloon;
        this.tenant = tenant;
    }

    public ScheduleDTO(int id,String nameEvent, String typeEvent, LocalDateTime dateEvent, int totalNumberGuests, List<SaloonDTO> saloon, TenantResponse tenant) {
        this.id = id;
        this.nameEvent = nameEvent;
        this.typeEvent = typeEvent;
        this.dateEvent = dateEvent;
        this.totalNumberGuests = totalNumberGuests;
        this.saloon = saloon;
        this.tenant = tenant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameEvent() {
        return nameEvent;
    }

    public void setNameEvent(String nameEvent) {
        this.nameEvent = nameEvent;
    }

    public String getTypeEvent() {
        return typeEvent;
    }

    public void setTypeEvent(String typeEvent) {
        this.typeEvent = typeEvent;
    }

    public LocalDateTime getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(LocalDateTime dateEvent) {
        this.dateEvent = dateEvent;
    }

    public int getTotalNumberGuests() {
        return totalNumberGuests;
    }

    public void setTotalNumberGuests(int totalNumberGuests) {
        this.totalNumberGuests = totalNumberGuests;
    }

    public List<SaloonDTO> getSaloon() {
        return saloon;
    }

    public void setSaloon(List<SaloonDTO> saloon) {
        this.saloon = saloon;
    }

    public TenantResponse getTenant() {
        return tenant;
    }

    public void setTenant(TenantResponse tenant) {
        this.tenant = tenant;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", nameEvent='" + nameEvent + '\'' +
                ", typeEvent='" + typeEvent + '\'' +
                ", dateEvent=" + dateEvent +
                ", totalNumberGuests=" + totalNumberGuests +
                ", saloon=" + saloon +
                ", tenant=" + tenant +
                '}';
    }
}

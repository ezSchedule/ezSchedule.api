package br.com.ezschedule.apischedule.model.DtoClasses;

import br.com.ezschedule.apischedule.model.DtoClasses.Response.TenantResponse;

import java.time.LocalDateTime;
import java.util.List;

public class ScheduleDTO {
    private int id;
    private String nameEvent;
    private String typeEvent;
    private String dateEvent;
    private int totalNumberGuests;

    public ScheduleDTO(int id, String nameEvent, String typeEvent, String dateEvent, int totalNumberGuests) {
        this.id = id;
        this.nameEvent = nameEvent;
        this.typeEvent = typeEvent;
        this.dateEvent = dateEvent;
        this.totalNumberGuests = totalNumberGuests;
    }

    public ScheduleDTO() {
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

    public String getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(String dateEvent) {
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

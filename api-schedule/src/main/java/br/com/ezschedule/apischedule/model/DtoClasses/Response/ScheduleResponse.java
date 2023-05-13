package br.com.ezschedule.apischedule.model.DtoClasses.Response;

import br.com.ezschedule.apischedule.model.DtoClasses.SaloonDTO;
import br.com.ezschedule.apischedule.model.DtoClasses.TenantDTO;
import java.time.LocalDateTime;

public class ScheduleResponse {
    private int id;
    private String nameEvent;
    private String typeEvent;
    private LocalDateTime dateEvent;
    private int totalNumberGuests;
    private SaloonDTO saloon;
    private TenantDTO tenant;

    public ScheduleResponse(int id, String nameEvent, String typeEvent, LocalDateTime dateEvent, int totalNumberGuests, SaloonDTO saloon, TenantDTO tenant) {
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

    public SaloonDTO getSaloon() {
        return saloon;
    }

    public void setSaloon(SaloonDTO saloon) {
        this.saloon = saloon;
    }

    public TenantDTO getTenant() {
        return tenant;
    }

    public void setTenant(TenantDTO tenant) {
        this.tenant = tenant;
    }
}

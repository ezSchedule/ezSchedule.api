package br.com.ezschedule.apischedule.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nameEvent;
    private String typeEvent;
    private LocalDateTime dateEvent;
    private int totalNumberGuests;

    private int isCanceled;

    @ManyToOne
    private Saloon saloon;

    @ManyToOne
    private Tenant tenant;


    public Schedule(String nameEvent, String typeEvent, Integer isCanceled, LocalDateTime dateEvent, int totalNumberGuests, Saloon saloon, Tenant tenant) {
        this.nameEvent = nameEvent;
        this.typeEvent = typeEvent;
        this.dateEvent = dateEvent;
        this.totalNumberGuests = totalNumberGuests;
        this.isCanceled = isCanceled;
        this.saloon = saloon;
        this.tenant = tenant;
    }

    public Schedule(int id, String nameEvent, String typeEvent, Integer isCanceled, LocalDateTime dateEvent, int totalNumberGuests, Saloon saloon, Tenant tenant) {
        this.id = id;
        this.nameEvent = nameEvent;
        this.typeEvent = typeEvent;
        this.isCanceled = isCanceled;
        this.dateEvent = dateEvent;
        this.totalNumberGuests = totalNumberGuests;
        this.saloon = saloon;
        this.tenant = tenant;
    }

    public Schedule() {
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

    public int getIsCanceled() {
        return isCanceled;
    }

    public void setIsCanceled(int isCanceled) {
        this.isCanceled = isCanceled;
    }

    public int getTotalNumberGuests() {
        return totalNumberGuests;
    }

    public void setTotalNumberGuests(int totalNumberGuests) {
        this.totalNumberGuests = totalNumberGuests;
    }

    public Saloon getSaloon() {
        return saloon;
    }

    public void setSaloon(Saloon saloon) {
        this.saloon = saloon;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
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

package br.com.ezschedule.apischedule.model.DtoClasses;

import java.time.LocalDateTime;

public class UpdateScheduleForm {

    private String nameEvent;
    private String typeEvent;
    private LocalDateTime dateEvent;
    private int totalNumberGuests;

    public UpdateScheduleForm(String nameEvent, String typeEvent, LocalDateTime dateEvent, int totalNumberGuests) {
        this.nameEvent = nameEvent;
        this.typeEvent = typeEvent;
        this.dateEvent = dateEvent;
        this.totalNumberGuests = totalNumberGuests;
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

    @Override
    public String toString() {
        return "UpdateScheduleForm{" +
                "nameEvent='" + nameEvent + '\'' +
                ", typeEvent='" + typeEvent + '\'' +
                ", dateEvent=" + dateEvent +
                ", totalNumberGuests=" + totalNumberGuests +
                '}';
    }
}
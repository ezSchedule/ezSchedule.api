package br.com.ezschedule.apischedule.model.DtoClasses;

public class InfoDate {
    private String month;
    private Integer totalGuests;
    private Integer totalEvents;

    public InfoDate(String month, Integer totalGuests, Integer totalEvents){
        this.month = month;
        this.totalGuests = totalGuests;
        this.totalEvents = totalEvents;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getTotalGuests() {
        return totalGuests;
    }

    public void setTotalGuests(Integer totalGuests) {
        this.totalGuests = totalGuests;
    }

    public Integer getTotalEvents() {
        return totalEvents;
    }

    public void setTotalEvents(Integer totalEvents) {
        this.totalEvents = totalEvents;
    }
}

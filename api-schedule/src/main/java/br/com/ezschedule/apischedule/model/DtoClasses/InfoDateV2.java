package br.com.ezschedule.apischedule.model.DtoClasses;

public class InfoDateV2 {
    private Integer month;
    private Integer totalGuests;
    private Integer totalEvents;

    public InfoDateV2(Integer month, Integer totalGuests, Integer totalEvents){
        this.month = month;
        this.totalGuests = totalGuests;
        this.totalEvents = totalEvents;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
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

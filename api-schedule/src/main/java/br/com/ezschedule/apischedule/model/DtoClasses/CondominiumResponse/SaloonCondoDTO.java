package br.com.ezschedule.apischedule.model.DtoClasses.CondominiumResponse;

import br.com.ezschedule.apischedule.model.Schedule;

public class SaloonCondoDTO {
    private int id;
    private String saloonName;
    private Double saloonPrice;
    private String saloonBlock;
    private Schedule schedule;

    public SaloonCondoDTO(int id, String saloonName, Double saloonPrice, String saloonBlock, Schedule schedule) {
        this.id = id;
        this.saloonName = saloonName;
        this.saloonPrice = saloonPrice;
        this.saloonBlock = saloonBlock;
        this.schedule = schedule;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSaloonName() {
        return saloonName;
    }

    public void setSaloonName(String saloonName) {
        this.saloonName = saloonName;
    }

    public Double getSaloonPrice() {
        return saloonPrice;
    }

    public void setSaloonPrice(Double saloonPrice) {
        this.saloonPrice = saloonPrice;
    }

    public String getSaloonBlock() {
        return saloonBlock;
    }

    public void setSaloonBlock(String saloonBlock) {
        this.saloonBlock = saloonBlock;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}

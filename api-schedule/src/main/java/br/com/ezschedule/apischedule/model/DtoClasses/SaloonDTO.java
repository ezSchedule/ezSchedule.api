package br.com.ezschedule.apischedule.model.DtoClasses;

import br.com.ezschedule.apischedule.model.Schedule;

import java.util.List;

public class SaloonDTO {


    private int id;
    private String saloonName;
    private Double saloonPrice;
    private String saloonBlock;

    private List<Schedule> schedule;

    private CondominiumDto condominium;

    public SaloonDTO(int id, String saloonName, Double saloonPrice, String saloonBlock, List<Schedule> schedule, CondominiumDto condominium) {
        this.id = id;
        this.saloonName = saloonName;
        this.saloonPrice = saloonPrice;
        this.saloonBlock = saloonBlock;
        this.schedule = schedule;
        this.condominium = condominium;
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

    public List<Schedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<Schedule> schedule) {
        this.schedule = schedule;
    }

    public CondominiumDto getCondominium() {
        return condominium;
    }

    public void setCondominium(CondominiumDto condominium) {
        this.condominium = condominium;
    }
}

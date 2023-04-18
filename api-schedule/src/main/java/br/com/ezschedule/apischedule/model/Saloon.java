package br.com.ezschedule.apischedule.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Saloon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String saloonName;
    private Double saloonPrice;
    private String saloonBlock;

    @ManyToOne
    private Schedule schedule;
    @ManyToOne
    private Condominium condominium;

    public Saloon(String saloonName, Double saloonPrice, String saloonBlock, Schedule schedule, Condominium condominium) {
        this.saloonName = saloonName;
        this.saloonPrice = saloonPrice;
        this.saloonBlock = saloonBlock;
        this.schedule = schedule;
        this.condominium = condominium;
    }

    public Saloon() {
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

    public Condominium getCondominium() {
        return condominium;
    }

    public void setCondominium(Condominium condominium) {
        this.condominium = condominium;
    }

    @Override
    public String toString() {
        return "Saloon{" +
                "id=" + id +
                ", saloonName='" + saloonName + '\'' +
                ", saloonPrice=" + saloonPrice +
                ", saloonBlock='" + saloonBlock + '\'' +
                ", schedule=" + schedule +
                ", condominium=" + condominium +
                '}';
    }
}

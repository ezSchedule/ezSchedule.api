package br.com.ezschedule.apischedule.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("2")
public class Tenant extends User {

    public Tenant(String email, String cpf, String password, String name, String residentsBlock, Integer apartmentNumber, String phoneNumber) {
        super(email, cpf, password, name, residentsBlock, apartmentNumber, phoneNumber);
    }

    public Tenant() {
    }
}

package br.com.ezschedule.apischedule.model;

import javax.persistence.Entity;

@Entity
public class Tenant extends User {

    public Tenant(String email, String cpf, String password, String name, String residentsBlock, Integer apartmentNumber, String phoneNumber, boolean isAdmin) {
        super(email, cpf, password, name, residentsBlock, apartmentNumber, phoneNumber, isAdmin);
    }

    public Tenant() {
    }
}

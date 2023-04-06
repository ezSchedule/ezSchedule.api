package br.com.ezschedule.apischedule.model;

import javax.persistence.*;

@Entity
public class Administrator extends User {

    public Administrator(String email, String cpf, String password, String name, String residentsBlock, Integer apartmentNumber, String phoneNumber, boolean isAdmin) {
        super(email, cpf, password, name, residentsBlock, apartmentNumber, phoneNumber, isAdmin);
    }

    public Administrator() {
    }


}

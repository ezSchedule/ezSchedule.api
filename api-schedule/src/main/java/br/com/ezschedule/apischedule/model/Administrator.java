package br.com.ezschedule.apischedule.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue("1")
public class Administrator extends User {

    public Administrator(String email, String cpf, String password, String name, String residentsBlock, Integer apartmentNumber, String phoneNumber) {
        super(email, cpf, password, name, residentsBlock, apartmentNumber, phoneNumber);
    }

    public Administrator() {
    }


}

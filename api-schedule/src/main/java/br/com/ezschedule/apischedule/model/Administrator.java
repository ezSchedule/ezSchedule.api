package br.com.ezschedule.apischedule.model;

import javax.persistence.*;
@Entity
public class Administrator extends User {

    public Administrator(String name, String phoneNumber, String email, String password, String cpf) {
        super(name, phoneNumber, email, password, cpf);
    }

    public Administrator() {
    }


}

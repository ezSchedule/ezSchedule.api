package br.com.ezschedule.apischedule.model;

import javax.persistence.Entity;

@Entity
public class Client extends User {

    public Client(String name, String phoneNumber, String email, String password, String cpf) {
        super(name, phoneNumber, email, password, cpf);
    }

    public Client() {
    }
}

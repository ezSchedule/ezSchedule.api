package br.com.ezschedule.apischedule.model;

public class UserJsonAnswer {
    private String name;
    private String phoneNumber;
    private String email;
    private String cpf;
    private boolean isAuthenticated;

    public UserJsonAnswer(String name, String phoneNumber, String email, String cpf, boolean isAuthenticated) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.cpf = cpf;
        this.isAuthenticated = isAuthenticated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }
}

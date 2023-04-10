package br.com.ezschedule.apischedule.model;

public class JsonResponse {

    private String email;

    private String cpf;

    private String name;

    private String residentsBlock;

    private Integer apartmentNumber;

    private String phoneNumber;

    private boolean isAuthenticated;

    private Boolean isAdmin;

    public JsonResponse(String email, String cpf, String name, String residentsBlock, Integer apartmentNumber, String phoneNumber, String number, boolean isAuthenticated, Boolean isAdmin) {
        this.email = email;
        this.cpf = cpf;
        this.name = name;
        this.residentsBlock = residentsBlock;
        this.apartmentNumber = apartmentNumber;
        this.phoneNumber = phoneNumber;
        this.isAuthenticated = isAuthenticated;
        this.isAdmin = isAdmin;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResidentsBlock() {
        return residentsBlock;
    }

    public void setResidentsBlock(String residentsBlock) {
        this.residentsBlock = residentsBlock;
    }

    public Integer getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(Integer apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}

package br.com.ezschedule.apischedule.model;

import org.hibernate.validator.constraints.br.CPF;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.INTEGER)
@Table(name = "Tenant")
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUser;

    @Email
    private String email;

    //    @CPF
    private String cpf;

    //    @NotBlank
//    @Size(min=8)
    private String password;

    @NotBlank
    @Size(min=3)
    private String name;

    private String residentsBlock;

    private Integer apartmentNumber;

    //    @Pattern(
//            regexp = "(\\(?\\d{2}\\)?\\s)?(\\d{4,5}\\-\\d{4})",
//            message = "Informe um telefone válido"
//    )
    private String phoneNumber;

    private boolean isAuthenticated;

    private Integer isAdmin;

    public User(String email, String cpf, String password, String name, String residentsBlock, Integer apartmentNumber, String phoneNumber) {
        this.email = email;
        this.cpf = cpf;
        this.password = password;
        this.name = name;
        this.residentsBlock = residentsBlock;
        this.apartmentNumber = apartmentNumber;
        this.phoneNumber = phoneNumber;
        this.isAuthenticated = false;
    }

    public User() {
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Integer isAdmin() {
        return isAdmin;
    }

    public void setAdmin(Integer admin) {
        isAdmin = admin;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", email='" + email + '\'' +
                ", cpf='" + cpf + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", residentsBlock='" + residentsBlock + '\'' +
                ", apartmentNumber=" + apartmentNumber +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", isAuthenticated=" + isAuthenticated +
                ", isAdmin=" + isAdmin +
                '}';
    }
}

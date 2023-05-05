package br.com.ezschedule.apischedule.model.DtoClasses;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TenantResponse {

    private int id;


    @Schema(description = "email do usuário", example = "teste@gmail.com")
    private String email;

    @Schema(description = "cpf do usuário", example = "50719658012")
    private String cpf;

    @Schema(description = "nome completo do usuário", example = "Teste Testando da Silva")
    private String name;

    @Schema(description = "bloco da residência", example = "bloco e")
    private String residentsBlock;

    @Schema(description = "número do apartemento", example = "14")
    private Integer apartmentNumber;

    @Schema(description = "número para contato", example = "11930733686")
    private String phoneNumber;

    @Schema(description = "usuário está logado", example = "false")
    private boolean isAuthenticated;

    @Schema(description = "usuário é um sindico", example = "1")
    private Integer isAdmin;

    public TenantResponse(int id,String email, String cpf, String name, String residentsBlock, Integer apartmentNumber, String phoneNumber, boolean isAuthenticated, Integer isAdmin) {
        this.id = id;
        this.email = email;
        this.cpf = cpf;
        this.name = name;
        this.residentsBlock = residentsBlock;
        this.apartmentNumber = apartmentNumber;
        this.phoneNumber = phoneNumber;
        this.isAuthenticated = isAuthenticated;
        this.isAdmin = isAdmin;
    }

    public TenantResponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }
}

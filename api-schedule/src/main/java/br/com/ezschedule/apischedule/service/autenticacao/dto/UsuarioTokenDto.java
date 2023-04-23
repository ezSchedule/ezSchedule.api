package br.com.ezschedule.apischedule.service.autenticacao.dto;

public class UsuarioTokenDto {

  private int id;
  private String email;
  private String cpf;
  private String name;
  private String password;
  private String residentsBlock;
  private Integer apartmentNumber;
  private String phoneNumber;
  private boolean isAuthenticated;
  private Integer isAdmin;
  private String token;

  public UsuarioTokenDto(int id, String email, String cpf, String name, String password, String residentsBlock, Integer apartmentNumber, String phoneNumber, boolean isAuthenticated, Integer isAdmin, String token) {
    this.id = id;
    this.email = email;
    this.cpf = cpf;
    this.name = name;
    this.password = password;
    this.residentsBlock = residentsBlock;
    this.apartmentNumber = apartmentNumber;
    this.phoneNumber = phoneNumber;
    this.isAuthenticated = isAuthenticated;
    this.isAdmin = isAdmin;
    this.token = token;
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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
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

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}

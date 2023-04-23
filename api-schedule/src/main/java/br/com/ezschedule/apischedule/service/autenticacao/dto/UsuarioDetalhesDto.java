package br.com.ezschedule.apischedule.service.autenticacao.dto;

import br.com.ezschedule.apischedule.model.Tenant;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UsuarioDetalhesDto implements UserDetails {

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

  public UsuarioDetalhesDto(Tenant t) {
    this.id = t.getIdUser();
    this.email = t.getEmail();
    this.cpf = t.getCpf();
    this.name = t.getName();
    this.password = t.getPassword();
    this.residentsBlock = t.getResidentsBlock();
    this.apartmentNumber = t.getApartmentNumber();
    this.phoneNumber = t.getPhoneNumber();
    this.isAuthenticated = t.isAuthenticated();
    this.isAdmin = t.isAdmin();
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

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}

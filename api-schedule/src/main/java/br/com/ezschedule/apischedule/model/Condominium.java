package br.com.ezschedule.apischedule.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Condominium {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    @OneToMany(mappedBy = "condominium",fetch = FetchType.LAZY)
    private List<Tenant> tenantList;
    @OneToMany(mappedBy = "condominium",fetch = FetchType.LAZY)
    private List<Saloon> saloonList;
    @OneToMany(mappedBy = "condominium",fetch = FetchType.LAZY)
    private List<Report> reportList;


    public Condominium(String name, String street, String city, String state, String postalCode, String country, List<Tenant> tenantList, List<Saloon> saloonList, List<Report> reportList) {
        this.name = name;
        this.street = street;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.tenantList = tenantList;
        this.saloonList = saloonList;
        this.reportList = reportList;
    }

    public Condominium(String name, String street, String city, String state, String postalCode, String country) {
        this.name = name;
        this.street = street;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.tenantList = new ArrayList<>();
        this.saloonList = new ArrayList<>();
        this.reportList = new ArrayList<>();
    }


    public Condominium(int id) {
        this.id = id;
    }

    public Condominium() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Tenant> getTenantList() {
        return tenantList;
    }

    public void setTenantList(List<Tenant> tenantList) {
        this.tenantList = tenantList;
    }

    public List<Saloon> getSaloonList() {
        return saloonList;
    }

    public void setSaloonList(List<Saloon> saloonList) {
        this.saloonList = saloonList;
    }

    public List<Report> getReportList() {
        return reportList;
    }

    public void setReportList(List<Report> reportList) {
        this.reportList = reportList;
    }

    public Condominium(int id, String name, String street, String city, String state, String postalCode, String country, List<Tenant> tenantList, List<Saloon> saloonList, List<Report> reportList) {
        this.id = id;
        this.name = name;
        this.street = street;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.tenantList = tenantList;
        this.saloonList = saloonList;
        this.reportList = reportList;
    }
}

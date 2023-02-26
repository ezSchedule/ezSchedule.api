package br.com.ezschedule.apischedule.model;

import java.util.ArrayList;
import java.util.List;

public class Condominium {
    private String name;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    private List<User> userList;

    public Condominium(String name, String street, String city, String state, String postalCode, String country,List<User> userList) {
        this.name = name;
        this.street = street;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.userList = userList;
    }

    public Condominium(String name, String street, String city, String state, String postalCode, String country) {
        this.name = name;
        this.street = street;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.userList = new ArrayList<>();
    }

    public Condominium() {
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

    @Override
    public String toString() {
        return "Condominium " +
                "name ='" + name + '\'' +
                ", street = '" + street + '\'' +
                ", city = '" + city + '\'' +
                ", state = '" + state + '\'' +
                ", postalCode = '" + postalCode + '\'' +
                ", country = '" + country + '\'' +
                ", userList = " + userList;
    }
}

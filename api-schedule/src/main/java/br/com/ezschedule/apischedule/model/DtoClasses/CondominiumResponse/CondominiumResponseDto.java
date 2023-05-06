package br.com.ezschedule.apischedule.model.DtoClasses.CondominiumResponse;

import br.com.ezschedule.apischedule.model.DtoClasses.TenantDTO;

import java.util.List;

public class CondominiumResponseDto {
    private int id;
    private String name;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private List<TenantDTO> tenantList;
    private List<SaloonCondoDTO> saloonList;
    private List<ForumCondoDTO> forumPostList;
    private List<ReportCondoDTO> reportList;

    public CondominiumResponseDto(int id, String name, String street, String city, String state, String postalCode, String country, List<TenantDTO> tenantList, List<SaloonCondoDTO> saloonList, List<ForumCondoDTO> forumPostList, List<ReportCondoDTO> reportList) {
        this.id = id;
        this.name = name;
        this.street = street;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.tenantList = tenantList;
        this.saloonList = saloonList;
        this.forumPostList = forumPostList;
        this.reportList = reportList;
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

    public List<TenantDTO> getTenantList() {
        return tenantList;
    }

    public void setTenantList(List<TenantDTO> tenantList) {
        this.tenantList = tenantList;
    }

    public List<SaloonCondoDTO> getSaloonCondoDTOList() {
        return saloonList;
    }

    public void setSaloonCondoDTOList(List<SaloonCondoDTO> saloonList) {
        this.saloonList = saloonList;
    }

    public List<ForumCondoDTO> getForumDTOList() {
        return forumPostList;
    }

    public void setForumDTOList(List<ForumCondoDTO> forumPostList) {
        this.forumPostList = forumPostList;
    }

    public List<ReportCondoDTO> getReportCondoDTOList() {
        return reportList;
    }

    public void setReportCondoDTOList(List<ReportCondoDTO> reportList) {
        this.reportList = reportList;
    }
}

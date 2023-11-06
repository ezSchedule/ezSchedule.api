package br.com.ezschedule.apischedule.model.DtoClasses;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReportPaymentsDto {
    private String id;
    private String productName;
    private String category;
    private String paymentStatus;
    private String paymentTime;
    private String tenantName;
    private String tenantBlock;
    private Integer tenantNumber;
    private String tenantPhone;
    private String dateEvent;
    private String saloonName;
    private Double saloonPrice;
    private String saloonBlock;

    public ReportPaymentsDto(String id, String productName, String category, String paymentStatus, LocalDateTime paymentTime, String tenantName, String tenantBlock, Integer tenantNumber, String tenantPhone, LocalDateTime dateEvent, String saloonName, Double saloonPrice, String saloonBlock) {
        this.id = id;
        this.productName = productName;
        this.category = category;
        this.paymentStatus = paymentStatus;
        this.paymentTime = paymentTime != null ? paymentTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : null;
        this.tenantName = tenantName;
        this.tenantBlock = tenantBlock;
        this.tenantNumber = tenantNumber;
        this.tenantPhone = tenantPhone;
        this.dateEvent = dateEvent.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.saloonName = saloonName;
        this.saloonPrice = saloonPrice;
        this.saloonBlock = saloonBlock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getTenantBlock() {
        return tenantBlock;
    }

    public void setTenantBlock(String tenantBlock) {
        this.tenantBlock = tenantBlock;
    }

    public Integer getTenantNumber() {
        return tenantNumber;
    }

    public void setTenantNumber(Integer tenantNumber) {
        this.tenantNumber = tenantNumber;
    }

    public String getTenantPhone() {
        return tenantPhone;
    }

    public void setTenantPhone(String tenantPhone) {
        this.tenantPhone = tenantPhone;
    }

    public String getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(String dateEvent) {
        this.dateEvent = dateEvent;
    }

    public String getSaloonName() {
        return saloonName;
    }

    public void setSaloonName(String saloonName) {
        this.saloonName = saloonName;
    }

    public Double getSaloonPrice() {
        return saloonPrice;
    }

    public void setSaloonPrice(Double saloonPrice) {
        this.saloonPrice = saloonPrice;
    }

    public String getSaloonBlock() {
        return saloonBlock;
    }

    public void setSaloonBlock(String saloonBlock) {
        this.saloonBlock = saloonBlock;
    }
}

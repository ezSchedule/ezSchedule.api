package br.com.ezschedule.apischedule.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Report {

    @Id
    private String id;
    private String invoiceNumber;
    private String productName;
    private String category;
    private String paymentStatus;
    private LocalDateTime paymentTime;
    @OneToOne
    private Schedule schedule;
    @ManyToOne
    private Condominium condominium;

    @ManyToOne
    private Tenant tenant;

    public Report(String id, String invoiceNumber, String productName, String category, String paymentStatus, LocalDateTime paymentTime, Schedule schedule, Condominium condominium, Tenant tenant) {
        this.id = id;
        this.invoiceNumber = invoiceNumber;
        this.productName = productName;
        this.category = category;
        this.paymentStatus = paymentStatus;
        this.paymentTime = paymentTime;
        this.schedule = schedule;
        this.condominium = condominium;
        this.tenant = tenant;
    }

    public Report() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return this.invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
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

    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(LocalDateTime paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Condominium getCondominium() {
        return condominium;
    }

    public void setCondominium(Condominium condominium) {
        this.condominium = condominium;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", invoiceNumber=" + invoiceNumber +
                ", productName='" + productName + '\'' +
                ", category='" + category + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", paymentTime=" + paymentTime +
                ", schedule=" + schedule +
                ", condominium=" + condominium +
                ", tenant=" + tenant +
                '}';
    }
}

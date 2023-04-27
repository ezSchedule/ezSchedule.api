package br.com.ezschedule.apischedule.model.DtoClasses.CondominiumResponse;

import br.com.ezschedule.apischedule.model.Condominium;
import br.com.ezschedule.apischedule.model.Schedule;
import br.com.ezschedule.apischedule.model.Tenant;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

public class ReportCondoDTO {
    private int id;
    private int invoiceNumber;
    private String productName;
    private String category;
    private String paymentStatus;
    private LocalDateTime paymentTime;
    private Schedule schedule;
    private Tenant tenant;

    public ReportCondoDTO(int id, int invoiceNumber, String productName, String category, String paymentStatus, LocalDateTime paymentTime, Schedule schedule, Tenant tenant) {
        this.id = id;
        this.invoiceNumber = invoiceNumber;
        this.productName = productName;
        this.category = category;
        this.paymentStatus = paymentStatus;
        this.paymentTime = paymentTime;
        this.schedule = schedule;
        this.tenant = tenant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(int invoiceNumber) {
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

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }
}

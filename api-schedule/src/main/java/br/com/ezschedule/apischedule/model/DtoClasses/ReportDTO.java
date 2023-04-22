package br.com.ezschedule.apischedule.model.DtoClasses;

import br.com.ezschedule.apischedule.model.Schedule;

import java.time.LocalDateTime;

public class ReportDTO {
    private int id;
    private int invoiceNumber;
    private String productName;
    private String category;
    private String paymentStatus;
    private LocalDateTime paymentTime;
    private Schedule schedule;
    private CondominiumDto condominium;
    private TenantResponse tenant;

    public ReportDTO(int id, int invoiceNumber, String productName, String category, String paymentStatus, LocalDateTime paymentTime, Schedule schedule, CondominiumDto condominium, TenantResponse tenant) {
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

    public ReportDTO() {
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

    public CondominiumDto getCondominium() {
        return condominium;
    }

    public void setCondominium(CondominiumDto condominium) {
        this.condominium = condominium;
    }

    public TenantResponse getTenant() {
        return tenant;
    }

    public void setTenant(TenantResponse tenant) {
        this.tenant = tenant;
    }
}

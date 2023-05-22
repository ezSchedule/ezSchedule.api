package br.com.ezschedule.apischedule.model.DtoClasses;

import java.time.LocalDateTime;

public class ReportDTO {
    private int id;
    private int invoiceNumber;
    private String productName;
    private String category;
    private String paymentStatus;
    private String paymentTime;

    public ReportDTO(int id, int invoiceNumber, String productName, String category, String paymentStatus, String paymentTime) {
        this.id = id;
        this.invoiceNumber = invoiceNumber;
        this.productName = productName;
        this.category = category;
        this.paymentStatus = paymentStatus;
        this.paymentTime = paymentTime;
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

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }
}

package br.com.ezschedule.apischedule.model.DtoClasses;

public class UpdatePixReport {
    private String txid;
    private String status;
    private String paymentTime;
    private String invoiceNumber;

    public UpdatePixReport(String txid, String status, String paymentTime, String invoiceNumber) {
        this.txid = txid;
        this.status = status;
        this.paymentTime = paymentTime;
        this.invoiceNumber = invoiceNumber;
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
}

package br.com.ezschedule.apischedule.model.DtoClasses;

import org.json.JSONObject;

public class PixRequest {
    private String name;
    private String cpf;
    private String value;
    private String paymentDescription;

    public PixRequest(String name, String cpf, String value, String paymentDescription) {
        this.name = name;
        this.cpf = cpf;
        this.value = value;
        this.paymentDescription = paymentDescription;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPaymentDescription() {
        return paymentDescription;
    }

    public void setPaymentDescription(String paymentDescription) {
        this.paymentDescription = paymentDescription;
    }

    public JSONObject getObjectAsJson(String pixKey) {
        JSONObject body = new JSONObject();
        body.put("calendario", new JSONObject().put("expiracao", 3600));
        body.put("devedor", new JSONObject().put("cpf", cpf).put("nome", name));
        body.put("valor", new JSONObject().put("original", value));
        body.put("chave", pixKey);
        body.put("solicitacaoPagador", paymentDescription);
        return body;
    }
}

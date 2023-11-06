package br.com.ezschedule.apischedule.model.DtoClasses;

import br.com.ezschedule.apischedule.model.Condominium;
import br.com.ezschedule.apischedule.model.Schedule;
import br.com.ezschedule.apischedule.model.Tenant;
import org.json.JSONObject;

public class PixRequest {
    private String name;
    private String cpf;
    private String value;
    private String paymentDescription;

    private Schedule schedule;
    private Condominium condominium;
    private Tenant tenant;


    public PixRequest(String name, String cpf, String value, String paymentDescription, Schedule schedule, Condominium condominium, Tenant tenant) {
        this.name = name;
        this.cpf = cpf;
        this.value = value;
        this.paymentDescription = paymentDescription;
        this.schedule = schedule;
        this.condominium = condominium;
        this.tenant = tenant;
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

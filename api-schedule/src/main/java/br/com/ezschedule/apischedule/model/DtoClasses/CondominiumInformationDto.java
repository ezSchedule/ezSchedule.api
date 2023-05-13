package br.com.ezschedule.apischedule.model.DtoClasses;

public class CondominiumInformationDto {
    private Integer amountTenants;
    private Integer amountApartments;
    private Integer amountSaloons;

    public CondominiumInformationDto(Integer amountTenants, Integer amountApartments, Integer amountSaloons) {
        this.amountTenants = amountTenants;
        this.amountApartments = amountApartments;
        this.amountSaloons = amountSaloons;
    }

    public Integer getAmountTenants() {
        return amountTenants;
    }

    public void setAmountTenants(Integer amountTenants) {
        this.amountTenants = amountTenants;
    }

    public Integer getAmountApartments() {
        return amountApartments;
    }

    public void setAmountApartments(Integer amountApartments) {
        this.amountApartments = amountApartments;
    }

    public Integer getAmountSaloons() {
        return amountSaloons;
    }

    public void setAmountSaloons(Integer amountSaloons) {
        this.amountSaloons = amountSaloons;
    }
}

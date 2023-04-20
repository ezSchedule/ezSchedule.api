package br.com.ezschedule.apischedule.model.DtoClasses;

public class UpdateSaloonForm {

    private String saloonName;
    private Double saloonPrice;
    private String saloonBlock;

    public UpdateSaloonForm(String saloonName, Double saloonPrice, String saloonBlock) {
        this.saloonName = saloonName;
        this.saloonPrice = saloonPrice;
        this.saloonBlock = saloonBlock;
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

    @Override
    public String toString() {
        return "UpdateSaloonForm{" +
                "saloonName='" + saloonName + '\'' +
                ", saloonPrice=" + saloonPrice +
                ", saloonBlock='" + saloonBlock + '\'' +
                '}';
    }
}

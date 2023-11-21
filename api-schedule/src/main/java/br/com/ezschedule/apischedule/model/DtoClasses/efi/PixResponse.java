package br.com.ezschedule.apischedule.model.DtoClasses.efi;

public class PixResponse {
    private String id;
    private String qrcode;
    private String imageQrcode;
    private String link;

    public PixResponse(String id,String qrCode, String imageQrCode, String link) {
        this.id = id;
        this.qrcode = qrCode;
        this.imageQrcode = imageQrCode;
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getImageQrcode() {
        return imageQrcode;
    }

    public void setImageQrcode(String imageQrcode) {
        this.imageQrcode = imageQrcode;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}

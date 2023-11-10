package br.com.ezschedule.apischedule.model.DtoClasses.efi;

public class PixResponse {
    private String qrcode;
    private String imageQrcode;
    private String link;

    public PixResponse(String qrCode, String imageQrCode, String link) {
        this.qrcode = qrCode;
        this.imageQrcode = imageQrCode;
        this.link = link;
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

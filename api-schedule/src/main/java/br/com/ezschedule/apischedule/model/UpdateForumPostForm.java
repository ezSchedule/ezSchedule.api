package br.com.ezschedule.apischedule.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class UpdateForumPostForm {
    @NotBlank
    @Size(min = 5)
    private String textContent;
    @NotBlank
    @Size(min = 5)
    private String typeMessage;

    public UpdateForumPostForm(String textContent, String typeMessage) {
        this.textContent = textContent;
        this.typeMessage = typeMessage;
    }

    public UpdateForumPostForm() {
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getTypeMessage() {
        return typeMessage;
    }

    public void setTypeMessage(String typeMessage) {
        this.typeMessage = typeMessage;
    }

    @Override
    public String toString() {
        return "UpdateForumPostForm{" +
                "textContent='" + textContent + '\'' +
                ", typeMessage='" + typeMessage + '\'' +
                '}';
    }
}

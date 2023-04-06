package br.com.ezschedule.apischedule.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class UpdateForumPostForm {

    private Integer id;
    @NotBlank
    @Size(min = 5)
    private String textContent;
    @NotBlank
    @Size(min = 5)
    private String typeMessage;

    private LocalDateTime dateTimePost;
    private boolean isEdited;

    public UpdateForumPostForm(String textContent, String typeMessage) {
        this.textContent = textContent;
        this.typeMessage = typeMessage;
        this.isEdited = true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public LocalDateTime getDateTimePost() {
        return dateTimePost;
    }

    public void setDateTimePost(LocalDateTime dateTimePost) {
        this.dateTimePost = dateTimePost;
    }

    public boolean isEdited() {
        return isEdited;
    }

    public void setEdited(boolean edited) {
        isEdited = edited;
    }

    @Override
    public String toString() {
        return "UpdateForumPostForm{" +
                "textContent='" + textContent + '\'' +
                ", typeMessage='" + typeMessage + '\'' +
                '}';
    }
}

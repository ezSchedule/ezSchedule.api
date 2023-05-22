package br.com.ezschedule.apischedule.model.DtoClasses;

import java.time.LocalDateTime;

public class ForumDTO {
    private Integer id;

    private String textContent;

    private String typeMessage;

    private String dateTimePost;
    private boolean isEdited;

    public ForumDTO(Integer id, String textContent, String typeMessage, String dateTimePost, boolean isEdited) {
        this.id = id;
        this.textContent = textContent;
        this.typeMessage = typeMessage;
        this.dateTimePost = dateTimePost;
        this.isEdited = isEdited;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getDateTimePost() {
        return dateTimePost;
    }

    public void setDateTimePost(String dateTimePost) {
        this.dateTimePost = dateTimePost;
    }

    public boolean isEdited() {
        return isEdited;
    }

    public void setEdited(boolean edited) {
        isEdited = edited;
    }
}

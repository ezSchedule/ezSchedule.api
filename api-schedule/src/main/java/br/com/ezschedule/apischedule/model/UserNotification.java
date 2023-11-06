package br.com.ezschedule.apischedule.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


public class UserNotification {


    private String textContent;

    private String typeMessage;
    private LocalDateTime dateTimePost = LocalDateTime.now();
    private boolean isEdited;
    @ManyToOne
    private Condominium condominium;

    public UserNotification(String textContent, String typeMessage, LocalDateTime dateTimePost, Condominium condominium) {
        this.textContent = textContent;
        this.dateTimePost = dateTimePost;
        this.typeMessage = typeMessage;
        this.condominium = condominium;
        isEdited = true;
    }

    public UserNotification(String textContent, String typeMessage, boolean isEdited) {
        this.textContent = textContent;
        this.typeMessage = typeMessage;
        this.isEdited = isEdited;
    }

    public UserNotification() {
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

    public Condominium getCondominium() {
        return condominium;
    }

    public void setCondominium(Condominium condominium) {
        this.condominium = condominium;
    }

    @Override
    public String toString() {
        return "ForumPosts{" +
                ", textContent='" + textContent + '\'' +
                ", typeMessage='" + typeMessage + '\'' +
                ", dateTimePost=" + dateTimePost +
                '}';
    }
}

package br.com.ezschedule.apischedule.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
public class ForumPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    @Size(min = 5)
    private String textContent;
    @NotBlank
    @Size(min = 5)
    private String typeMessage;
    @PastOrPresent
    private LocalDateTime dateTimePost;
    private boolean isEdited;
    @ManyToOne
    private Condominium condominium;


    public ForumPost(String textContent, String typeMessage, LocalDateTime dateTimePost, boolean isEdited, Condominium condominium) {
        this.textContent = textContent;
        this.typeMessage = typeMessage;
        this.dateTimePost = dateTimePost;
        this.isEdited = isEdited;
        this.condominium = condominium;
    }

    public ForumPost(Integer id, String textContent, String typeMessage, LocalDateTime dateTimePost, boolean isEdited) {
        this.id = id;
        this.textContent = textContent;
        this.typeMessage = typeMessage;
        this.dateTimePost = dateTimePost;
        this.isEdited = isEdited;
    }
    public ForumPost(String textContent, String typeMessage, LocalDateTime dateTimePost, Condominium condominium) {
        this.textContent = textContent;
        this.typeMessage = typeMessage;
        this.dateTimePost = dateTimePost;
        this.isEdited = false;
        this.condominium = condominium;
    }

    public ForumPost() {
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
                "id=" + id +
                ", textContent='" + textContent + '\'' +
                ", typeMessage='" + typeMessage + '\'' +
                ", dateTimePost=" + dateTimePost +
                '}';
    }
}

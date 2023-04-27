package br.com.ezschedule.apischedule.model.DtoClasses;

import br.com.ezschedule.apischedule.model.DtoClasses.CondominiumResponse.CondominiumResponseDto;

import java.time.LocalDateTime;

public class ForumDTO {


    private Integer id;

    private String textContent;

    private String typeMessage;

    private LocalDateTime dateTimePost;
    private boolean isEdited;

    private CondominiumDto condominium;

    public ForumDTO(Integer id, String textContent, String typeMessage, LocalDateTime dateTimePost, boolean isEdited, CondominiumDto condominium) {
        this.id = id;
        this.textContent = textContent;
        this.typeMessage = typeMessage;
        this.dateTimePost = dateTimePost;
        this.isEdited = isEdited;
        this.condominium = condominium;
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

    public CondominiumDto getCondominium() {
        return condominium;
    }

    public void setCondominium(CondominiumDto condominium) {
        this.condominium = condominium;
    }
}

package br.com.ezschedule.apischedule.adapter;

import br.com.ezschedule.apischedule.model.*;

public interface JsonResponseAdapter {

    public static JsonResponse Dto(Tenant c) {
        return new JsonResponse(
                c.getEmail(),
                c.getCpf(),
                c.getPassword(),
                c.getName(),
                c.getResidentsBlock(),
                c.getApartmentNumber(),
                c.getPhoneNumber(),
                c.getPhoneNumber(),
                c.isAuthenticated(),
                c.isAdmin());

    }

    public static JsonResponse Dto(Administrator a) {
        return new JsonResponse(
                a.getEmail(),
                a.getCpf(),
                a.getPassword(),
                a.getName(),
                a.getResidentsBlock(),
                a.getApartmentNumber(),
                a.getPhoneNumber(),
                a.getPhoneNumber(),
                a.isAuthenticated(),
                a.isAdmin());
    }

    public static ForumPost forumDTO(UpdateForumPostForm f){
        return new ForumPost(
                f.getId(),
                f.getTextContent(),
                f.getTypeMessage(),
                f.getDateTimePost(),
                f.isEdited());
    }

}
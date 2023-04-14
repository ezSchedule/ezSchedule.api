package br.com.ezschedule.apischedule.adapter;

import br.com.ezschedule.apischedule.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface JsonResponseAdapter {

    public static List<JsonResponse> convertJsonResponseList(List<Administrator> users) {
        List<JsonResponse> usersNoPassword = new ArrayList<>();
        for (int i = 0; i < users.size();i++) {
            usersNoPassword.add(JsonResponseAdapter.Dto(users.get(i)));
        }
        return usersNoPassword;
    }

    public static JsonResponse Dto(Tenant c) {
        return new JsonResponse(
                c.getEmail(),
                c.getCpf(),
                c.getName(),
                c.getResidentsBlock(),
                c.getApartmentNumber(),
                c.getPhoneNumber(),
                c.isAuthenticated(),
                c.isAdmin());

    }

    public static JsonResponse Dto(Administrator a) {
        return new JsonResponse(
                a.getEmail(),
                a.getCpf(),
                a.getName(),
                a.getResidentsBlock(),
                a.getApartmentNumber(),
                a.getPhoneNumber(),
                a.isAuthenticated(),
                a.isAdmin());
    }

    public static ForumPost forumDTO(UpdateForumPostForm f, int id, LocalDateTime time){
        return new ForumPost(
                id,
                f.getTextContent(),
                f.getTypeMessage(),
                time,
                true);
    }

}

package br.com.ezschedule.apischedule.adapter;

import br.com.ezschedule.apischedule.model.*;
import br.com.ezschedule.apischedule.model.DtoClasses.JsonResponse;
import br.com.ezschedule.apischedule.model.DtoClasses.UpdateForumPostForm;
import br.com.ezschedule.apischedule.model.DtoClasses.UpdateSaloonForm;
import br.com.ezschedule.apischedule.model.DtoClasses.UpdateScheduleForm;

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

//    public static ForumPost forumDTO(UpdateForumPostForm f, int id, LocalDateTime time){
//        return new ForumPost(
//                id,
//                f.getTextContent(),
//                f.getTypeMessage(),
//                time,
//                true);
//    }
//
//    public static Saloon saloonDTO(UpdateSaloonForm s, int id,Condominium c){
//        return new Saloon(
//                id,
//                s.getSaloonName(),
//                s.getSaloonPrice(),
//                s.getSaloonBlock(),
//                c
//        );
//    }
//
//    public static Schedule scheduleDTO(UpdateScheduleForm u,int id,List<Saloon> saloonList,Tenant t){
//        return new Schedule(
//                id,
//                u.getNameEvent(),
//                u.getTypeEvent(),
//                u.getDateEvent(),
//                u.getTotalNumberGuests(),
//                saloonList,
//                t
//        );
//    }

}

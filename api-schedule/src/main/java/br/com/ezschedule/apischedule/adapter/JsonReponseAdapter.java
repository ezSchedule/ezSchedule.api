package br.com.ezschedule.apischedule.adapter;

import br.com.ezschedule.apischedule.model.Administrator;
import br.com.ezschedule.apischedule.model.JsonResponse;
import br.com.ezschedule.apischedule.model.Client;
public interface JsonReponseAdapter {

    public static JsonResponse Dto(Client c){
        JsonResponse jsonAnswer = new JsonResponse(c.getName(),c.getPhoneNumber(),c.getEmail(),c.getCpf(), c.isAuthenticated());
        return jsonAnswer;
    }

    public static JsonResponse Dto(Administrator a){
        JsonResponse jsonAnswer = new JsonResponse(a.getName(),a.getPhoneNumber(),a.getEmail(),a.getCpf(), a.isAuthenticated());
        return jsonAnswer;
    }

}

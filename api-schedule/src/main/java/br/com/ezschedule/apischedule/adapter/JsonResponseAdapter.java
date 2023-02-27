package br.com.ezschedule.apischedule.adapter;

import br.com.ezschedule.apischedule.model.Administrator;
import br.com.ezschedule.apischedule.model.Client;
import br.com.ezschedule.apischedule.model.JsonResponse;

public interface JsonResponseAdapter {

    public static JsonResponse Dto(Client c) {
        return new JsonResponse(
                c.getName(),
                c.getPhoneNumber(),
                c.getEmail(),
                c.getCpf(),
                c.isAuthenticated()
        );
    }

    public static JsonResponse Dto(Administrator a) {
        return new JsonResponse(
                a.getName(),
                a.getPhoneNumber(),
                a.getEmail(),
                a.getCpf(),
                a.isAuthenticated());
    }

}

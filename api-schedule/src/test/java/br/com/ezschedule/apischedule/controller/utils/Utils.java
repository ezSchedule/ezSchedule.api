package br.com.ezschedule.apischedule.controller.utils;

import br.com.ezschedule.apischedule.model.Condominium;
import br.com.ezschedule.apischedule.model.Tenant;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static List<Condominium> createList() {

        List<Condominium> listCondominiums = new ArrayList<>();

        Condominium c = new Condominium(
                "Test",
                "SP",
                "São Paulo",
                "São Paulo",
                "123",
                "Brasil"
        );

        listCondominiums.add(c);

        return listCondominiums;
    }

    public static List<Tenant> createListTenant() {

        List<Tenant> listTenants = new ArrayList<>();

        Tenant c = new Tenant(
                1,
                "teste@gmail.com",
                "12345678901",
                "123Teste#",
                "Andrew Ferrari",
                "1B",
                32,
                "11961748392",
                0
        );

        listTenants.add(c);

        return listTenants;
    }

    public static Tenant createTenant() {

        return new Tenant(
                1,
                "teste@gmail.com",
                "12345678901",
                "123Teste#",
                "Andrew Ferrari",
                "1B",
                32,
                "11961748392",
                0
        );
    }
}

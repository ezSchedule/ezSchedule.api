package br.com.ezschedule.apischedule.service;

import br.com.ezschedule.apischedule.controller.utils.Utils;
import br.com.ezschedule.apischedule.model.Condominium;
import br.com.ezschedule.apischedule.model.Tenant;
import br.com.ezschedule.apischedule.repository.TenantRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TenantServiceTest {

    @Mock
    private TenantRepository repository;

    @InjectMocks
    private TenantService service;

    @Test
    @DisplayName("should not return any condo when list is empty")
    void shouldNotReturnAnyCondoWhenListIsEmpty() {

        List<Tenant> listTenants = new ArrayList<>();

        Mockito.when(repository.findAll()).thenReturn(listTenants);

        List<Tenant> tenants = service.listAllTenant();

        assertTrue(tenants.isEmpty());
    }

    @Test
    @DisplayName("should return a list of tenants")
    void shouldReturnListOfTenants() {

        int expectedSize = 1;
        List<Tenant> list = Utils.createListTenant();

        Mockito.when(repository.findAll()).thenReturn(list);

        List<Tenant> tenants = service.listAllTenant();

        assertEquals(expectedSize, tenants.size());
        assertEquals(list.get(0).getEmail(), tenants.get(0).getEmail());
        assertEquals(list.get(0).getName(), tenants.get(0).getName());
        assertEquals(list.get(0).getApartmentNumber(), tenants.get(0).getApartmentNumber());
        assertEquals(list.get(0).getCpf(), tenants.get(0).getCpf());
        assertEquals(list.get(0).getIdUser(), tenants.get(0).getIdUser());
        assertEquals(list.get(0).getPassword(), tenants.get(0).getPassword());
        assertEquals(list.get(0).getPhoneNumber(), tenants.get(0).getPhoneNumber());
        assertEquals(list.get(0).getResidentsBlock(), tenants.get(0).getResidentsBlock());

    }

    @Test
    @DisplayName("must return a tenant when the method is called")
    void mustReturnTenantWhenTheMethodIsCalled() {

        Integer updateSimulation = 1;
        String email = "teste@gmail.com";

        Mockito.when(repository.logoutUser(email)).thenReturn(updateSimulation);

        Object o = service.logoutTenant(email);

        assertTrue(o.equals(1));

    }

    @Test
    @DisplayName("must not return a tenant when the method is called")
    void mustNotReturnTenantWhenTheMethodIsCalled() {

        Integer updateSimulation = 0;
        String email = "teste@gmail.com";

        Mockito.when(repository.logoutUser(email)).thenReturn(updateSimulation);

        Object o = service.logoutTenant(email);

        assertTrue(o.equals(0));

    }

    @Test
    @DisplayName("should return one when entering a valid password")
    void shouldReturnOneWhenEnteringValidPassword() {

        Integer updateSimulation = 1;
        String email = "teste@gmail.com";
        String password = "teste123@";

        Mockito.when(repository.updatePasswordUser(email, password)).thenReturn(updateSimulation);

        Object o = service.updatePasswordTenant(email, password);

        assertTrue(o.equals(1));

    }

    @Test
    @DisplayName("should return zero when entering a valid password")
    void shouldReturnZeroWhenEnteringValidPassword() {

        Integer updateSimulation = 0;
        String email = "teste@gmail.com";
        String password = "teste123@";

        Mockito.when(repository.updatePasswordUser(email, password)).thenReturn(updateSimulation);

        Object o = service.updatePasswordTenant(email, password);

        assertTrue(o.equals(0));

    }

}
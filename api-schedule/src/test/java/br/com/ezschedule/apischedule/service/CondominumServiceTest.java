package br.com.ezschedule.apischedule.service;

import br.com.ezschedule.apischedule.controller.utils.Utils;
import br.com.ezschedule.apischedule.model.Condominium;
import br.com.ezschedule.apischedule.repository.CondominumRepository;
import br.com.ezschedule.apischedule.service.CondominumService;
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
class CondominumServiceTest {

    @Mock
    private CondominumRepository repository;

    @InjectMocks
    private CondominumService service;

    @Test
    @DisplayName("should not return any condo when list is empty")
    void shouldNotReturnAnyCondoWhenListIsEmpty() {

        List<Condominium> listCondominiums = new ArrayList<>();

        Mockito.when(repository.findAll()).thenReturn(listCondominiums);

        List<Condominium> condominiums = service.listAll();

        assertTrue(condominiums.isEmpty());
    }

    @Test
    @DisplayName("should return a list of condominiums")
    void shouldReturnListOfCondominiums() {

        int expectedSize = 1;
        List<Condominium> list = Utils.createList();

        Mockito.when(repository.findAll()).thenReturn(list);

        List<Condominium> condominiums = service.listAll();

        assertEquals(expectedSize, condominiums.size());
        assertEquals(list.get(0).getId(), condominiums.get(0).getId());
        assertEquals(list.get(0).getName(), condominiums.get(0).getName());
        assertEquals(list.get(0).getStreet(), condominiums.get(0).getStreet());
        assertEquals(list.get(0).getCity(), condominiums.get(0).getCity());
        assertEquals(list.get(0).getStreet(), condominiums.get(0).getStreet());
        assertEquals(list.get(0).getPostalCode(), condominiums.get(0).getPostalCode());
        assertEquals(list.get(0).getCountry(), condominiums.get(0).getCountry());
    }

}
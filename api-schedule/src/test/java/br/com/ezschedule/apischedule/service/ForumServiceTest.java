package br.com.ezschedule.apischedule.service;

import br.com.ezschedule.apischedule.model.DtoClasses.Response.ForumResponse;
import br.com.ezschedule.apischedule.model.ForumPost;
import br.com.ezschedule.apischedule.repository.ForumRepository;
import br.com.ezschedule.apischedule.service.ForumService;
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
class ForumServiceTest {

    @Mock
    private ForumRepository repository;

    @InjectMocks
    private ForumService forumService;

//    @Test
//    @DisplayName("should return empty list when there is no forum")
//    void shouldReturnEmptyListWhenThereIsNoForum() {
//
//        List<ForumPost> foruns = new ArrayList<>();
//
//        Mockito.when(repository.findAll()).thenReturn(foruns);
//
//        List<ForumResponse> result = forumService.listForuns();
//
//        assertTrue(result.isEmpty());
//    }

}
package br.com.ezschedule.apischedule.service;

import br.com.ezschedule.apischedule.adapter.JsonResponseAdapter;
import br.com.ezschedule.apischedule.model.DtoClasses.Response.ForumResponse;
import br.com.ezschedule.apischedule.model.ForumPost;
import br.com.ezschedule.apischedule.repository.ForumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ForumService {

    @Autowired
    private ForumRepository forumRepository;

    public List<ForumResponse> listForuns() {
        List<ForumPost> allPosts = forumRepository.findAll();
        return JsonResponseAdapter.listForumResponse(allPosts);
    }
}

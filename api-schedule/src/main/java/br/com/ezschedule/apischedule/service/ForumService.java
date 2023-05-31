package br.com.ezschedule.apischedule.service;

import br.com.ezschedule.apischedule.adapter.JsonResponseAdapter;
import br.com.ezschedule.apischedule.email.SendMail;
import br.com.ezschedule.apischedule.model.DtoClasses.Response.ForumResponse;
import br.com.ezschedule.apischedule.model.DtoClasses.UpdateResponse.UpdateForumPostForm;
import br.com.ezschedule.apischedule.model.ForumPost;
import br.com.ezschedule.apischedule.model.Tenant;
import br.com.ezschedule.apischedule.observer.FilaObj;
import br.com.ezschedule.apischedule.observer.SubscribedTenants;
import br.com.ezschedule.apischedule.repository.ForumRepository;
import br.com.ezschedule.apischedule.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class ForumService {
    @Autowired
    private ForumRepository forumRepository;

    @Autowired
    TenantRepository tenantRepository;
    @Autowired
    SubscribedTenants subscribedTenants;

    @Autowired
    private SendMail sendMail;

    private FilaObj<Tenant> forumPostFila = new FilaObj<>(100);

    public ResponseEntity<List<ForumResponse>> getAllPosts(){
        List<ForumPost> allPosts = forumRepository.findAll();

        if (allPosts.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(JsonResponseAdapter.listForumResponse(allPosts));
    }

    public ResponseEntity<ForumResponse> getById(int id) {
        if (forumRepository.findById(id).isPresent()) {
            return ResponseEntity.status(200).body(JsonResponseAdapter.forumResponse(forumRepository.findById(id).get()));
        }
        return ResponseEntity.status(404).build();
    }

    public ResponseEntity<ForumResponse> savePost(ForumPost post) {
        forumRepository.save(post);
        //descomente se deseje enviar emails para todos usuários inscritos
//        sendMails(post);

        return ResponseEntity.status(201).build();
    }

    public ResponseEntity<ForumResponse> updatePostById(UpdateForumPostForm updatePost,int id) {
        if (forumRepository.findById(id).isPresent()) {
            Optional<ForumPost> oldPost = forumRepository.findById(id);
            ForumPost updatedPost = JsonResponseAdapter.updateForumDTO(updatePost, id, oldPost.get());
            forumRepository.save(updatedPost);
            return ResponseEntity.status(200).body(JsonResponseAdapter.forumResponse(updatedPost));
        }
        return ResponseEntity.status(404).build();
    }

    public ResponseEntity<Void> deletePostById(int id) {
        if (forumRepository.existsById(id)) {
            forumRepository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }

    public ResponseEntity<List<ForumResponse>> getPostByType(String content) {
        List<ForumPost> forumPostList = forumRepository.findBasedOnType(content);
        if (forumPostList.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(JsonResponseAdapter.listForumResponse(forumPostList));
    }

    public ResponseEntity<List<ForumResponse>> getPostsByCondominium(int id) {
        List<ForumPost> forumPostList = forumRepository.findBasedOnCondominiumId(id);
        if (forumPostList.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(JsonResponseAdapter.listForumResponse(forumPostList));
    }


    public void sendMails(ForumPost p){
        List<Tenant> tenantList = tenantRepository.findSubscribedTenants(p.getCondominium().getId());

        for (Tenant t : tenantList) {
            forumPostFila.insert(t);
        }

        for (int i = 0; forumPostFila.getTamanho() > 0; i++) {
            sendEmailsOnADelayBasis(forumPostFila.poll(), p);
        }
    }

    @Scheduled(fixedRate = 5000)
    public void sendEmailsOnADelayBasis(Tenant t, ForumPost f) {
        subscribedTenants.notifySubscribers(t, f);
    }
}

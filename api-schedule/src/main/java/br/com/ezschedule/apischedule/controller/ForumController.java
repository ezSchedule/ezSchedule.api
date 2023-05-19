package br.com.ezschedule.apischedule.controller;

import br.com.ezschedule.apischedule.adapter.JsonResponseAdapter;
import br.com.ezschedule.apischedule.email.SendMail;
import br.com.ezschedule.apischedule.model.DtoClasses.Response.ForumResponse;
import br.com.ezschedule.apischedule.model.DtoClasses.UpdateResponse.UpdateForumPostForm;
import br.com.ezschedule.apischedule.model.ForumPost;
import br.com.ezschedule.apischedule.model.Tenant;
import br.com.ezschedule.apischedule.observer.EmailNotifier;
import br.com.ezschedule.apischedule.observer.FilaObj;
import br.com.ezschedule.apischedule.observer.SubscribedTenants;
import br.com.ezschedule.apischedule.repository.ForumRepository;
import br.com.ezschedule.apischedule.repository.TenantRepository;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Api(value = "Fórum", produces = MediaType.APPLICATION_JSON_VALUE, tags = {"forum"}, description = "requisições relacionadas ao fórum")
@RestController
@RequestMapping("${uri.forum}")
public class ForumController {

    @Autowired
    private ForumRepository forumRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    SubscribedTenants subscribedTenants;

    @Autowired
    private SendMail sendMail;

    private SubscribedTenants observer = new SubscribedTenants();

    private FilaObj<Tenant> forumPostFila = new FilaObj<>(100);


    @ApiResponse(responseCode = "204", description =
            "Não há fóruns para exibir.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "fóruns postados.")
    @GetMapping
    public ResponseEntity<List<ForumResponse>> showAllPosts() {
        List<ForumPost> allPosts = forumRepository.findAll();
        if (allPosts.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(201).build();
    }

    @ApiResponse(responseCode = "404", description =
            "Não foi encontrado nenhum fórum.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "fórum encontrado.")
    @GetMapping("/{id}")
    public ResponseEntity<ForumResponse> getPostById(@PathVariable int id) {
        if (forumRepository.findById(id).isPresent()) {
            return ResponseEntity.status(200).body(JsonResponseAdapter.forumResponse(forumRepository.findById(id).get()));
        }
        return ResponseEntity.status(404).build();
    }

    @ApiResponse(responseCode = "201", description =
            "fórum cadastrado", content = @Content(schema = @Schema(hidden = true)))
    @PostMapping
    public ResponseEntity<ForumResponse> newPost(@RequestBody @Valid ForumPost post) {
        forumRepository.save(post);

        //descomente se deseje enviar emails para todos usuários inscritos

        List<Tenant> tenantList = forumRepository.findSubscribedTenants();

//        for(Tenant t :tenantList){
//            forumPostFila.insert(t);
//        }
//
//        for(int i = 0;forumPostFila.getTamanho() > 0 ;i++){
//            sendEmailsOnADelayBasis(forumPostFila.poll(),post);
//        }

        return ResponseEntity.status(200).body(JsonResponseAdapter.forumResponse(post));
    }

    @ApiResponse(responseCode = "404", description =
            "Nenhum fórum encontrado.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "Fórum atualizado.")
    @PutMapping("/{id}")
    public ResponseEntity<ForumResponse> updatePostById(@RequestBody @Valid UpdateForumPostForm updatePost, @PathVariable int id) {
        if (forumRepository.findById(id).isPresent()) {
            Optional<ForumPost> oldPost = forumRepository.findById(id);
            ForumPost updatedPost = JsonResponseAdapter.updateForumDTO(updatePost, id, oldPost.get());
            forumRepository.save(updatedPost);
            return ResponseEntity.status(200).body(JsonResponseAdapter.forumResponse(updatedPost));
        }
        return ResponseEntity.status(404).build();
    }

    @ApiResponse(responseCode = "404", description =
            "Nenhum fórum encontrado.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "fórum deletado.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePostById(@PathVariable int id) {
        if (forumRepository.existsById(id)) {
            forumRepository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }


    @Scheduled(fixedRate = 5000)
    public void sendEmailsOnADelayBasis(Tenant t, ForumPost f){
        subscribedTenants.notifySubscribers(t,f);
    }

}

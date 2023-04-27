package br.com.ezschedule.apischedule.controller;

import br.com.ezschedule.apischedule.adapter.JsonResponseAdapter;
import br.com.ezschedule.apischedule.model.DtoClasses.CondominiumResponse.ForumCondoDTO;
import br.com.ezschedule.apischedule.model.DtoClasses.ForumDTO;
import br.com.ezschedule.apischedule.model.DtoClasses.UpdateForumPostForm;
import br.com.ezschedule.apischedule.model.ForumPost;
import br.com.ezschedule.apischedule.repository.ForumRepository;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @ApiResponse(responseCode = "204", description =
            "Não há fóruns para exibir.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "fóruns postados.")
    @GetMapping
    public ResponseEntity<List<ForumDTO>> showAllPosts(){
       List<ForumPost> allPosts = forumRepository.findAll();
       if(allPosts.isEmpty()){
           return ResponseEntity.status(204).build();
       }
       return ResponseEntity.status(200).body(JsonResponseAdapter.listForumDTO(allPosts));
    }

    @ApiResponse(responseCode = "404", description =
            "Não foi encontrado nenhum fórum.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "fórum encontrado.")
    @GetMapping("/{id}")
    public ResponseEntity<ForumDTO> getPostById(@PathVariable int id){
        if(forumRepository.findById(id).isPresent()){
            return ResponseEntity.status(200).body(JsonResponseAdapter.forumDTO(forumRepository.findById(id).get()));
        }
        return ResponseEntity.status(404).build();
    }

    @ApiResponse(responseCode = "201", description =
            "fórum cadastrado", content = @Content(schema = @Schema(hidden = true)))
    @PostMapping
    public ResponseEntity<ForumDTO> newPost(@RequestBody @Valid ForumPost post){
        forumRepository.save(post);
        return ResponseEntity.status(200).body(JsonResponseAdapter.forumDTO(post));
    }

    @ApiResponse(responseCode = "404", description =
            "Nenhum fórum encontrado.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "Fórum atualizado.")
    @PutMapping("/{id}")
    public ResponseEntity<ForumDTO> updatePostById(@RequestBody @Valid UpdateForumPostForm updatePost, @PathVariable int id){
        if(forumRepository.findById(id).isPresent()){
         Optional<ForumPost> oldPost = forumRepository.findById(id);
         ForumPost updatedPost = JsonResponseAdapter.updateForumDTO(updatePost,id,oldPost.get());
         forumRepository.save(updatedPost);
         return ResponseEntity.status(200).body(JsonResponseAdapter.forumDTO(updatedPost));
        }
        return ResponseEntity.status(404).build();
    }

    @ApiResponse(responseCode = "404", description =
            "Nenhum fórum encontrado.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "fórum deletado.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePostById(@PathVariable int id){
        if(forumRepository.existsById(id)){
            forumRepository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }

}

package br.com.ezschedule.apischedule.controller;

import br.com.ezschedule.apischedule.model.DtoClasses.Response.ForumResponse;
import br.com.ezschedule.apischedule.model.DtoClasses.UpdateResponse.UpdateForumPostForm;
import br.com.ezschedule.apischedule.model.ForumPost;
import br.com.ezschedule.apischedule.service.ForumService;
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

@Api(value = "Fórum", produces = MediaType.APPLICATION_JSON_VALUE, tags = {"forum"}, description = "requisições relacionadas ao fórum")
@RestController
@RequestMapping("${uri.forum}")
public class ForumController {

    @Autowired
    ForumService forumService;

    @ApiResponse(responseCode = "204", description = "Não há fóruns para exibir.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "fóruns postados.")
    @GetMapping
    public ResponseEntity<List<ForumResponse>> showAllPosts() {
        return forumService.getAll();
    }

    @ApiResponse(responseCode = "404", description = "Não foi encontrado nenhum fórum.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "fórum encontrado.")
    @GetMapping("/{id}")
    public ResponseEntity<ForumResponse> getPostById(@PathVariable int id) {
        return forumService.getById(id);
    }

    @ApiResponse(responseCode = "201", description = "post cadastrado", content = @Content(schema = @Schema(hidden = true)))
    @PostMapping
    public ResponseEntity<ForumResponse> newPost(@RequestBody @Valid ForumPost post) {
        return forumService.save(post);
    }

    @ApiResponse(responseCode = "404", description = "Nenhum post encontrado.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "post atualizado.")
    @PutMapping("/{id}")
    public ResponseEntity<ForumResponse> updatePostById(@RequestBody @Valid UpdateForumPostForm updatePost, @PathVariable int id) {
        return forumService.update(updatePost, id);
    }

    @ApiResponse(responseCode = "404", description = "Nenhum fórum encontrado.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "fórum deletado.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePostById(@PathVariable int id) {
        return forumService.delete(id);
    }

    @GetMapping("/type/{content}")
    public ResponseEntity<List<ForumResponse>> getBasedOnType(@PathVariable String content) {
        return forumService.getPostByType(content);
    }

    @GetMapping("/condominium/{id}")
    public ResponseEntity<List<ForumResponse>> getAllPostsByCondominium(@PathVariable int id) {
        return forumService.getPostsByCondominium(id);
    }
}

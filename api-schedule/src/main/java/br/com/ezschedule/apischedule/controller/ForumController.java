package br.com.ezschedule.apischedule.controller;

import br.com.ezschedule.apischedule.adapter.JsonResponseAdapter;
import br.com.ezschedule.apischedule.model.DtoClasses.CondominiumResponse.ForumCondoDTO;
import br.com.ezschedule.apischedule.model.DtoClasses.ForumDTO;
import br.com.ezschedule.apischedule.model.DtoClasses.UpdateForumPostForm;
import br.com.ezschedule.apischedule.model.ForumPost;
import br.com.ezschedule.apischedule.repository.ForumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${uri.forum}")
public class ForumController {

    @Autowired
    private ForumRepository forumRepository;

    @GetMapping
    public ResponseEntity<List<ForumDTO>> showAllPosts(){
       List<ForumPost> allPosts = forumRepository.findAll();
       if(allPosts.isEmpty()){
           return ResponseEntity.status(204).build();
       }
       return ResponseEntity.status(200).body(JsonResponseAdapter.listForumDTO(allPosts));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ForumDTO> getPostById(@PathVariable int id){
        if(forumRepository.findById(id).isPresent()){
            return ResponseEntity.status(200).body(JsonResponseAdapter.forumDTO(forumRepository.findById(id).get()));
        }
        return ResponseEntity.status(404).build();
    }

    @PostMapping
    public ResponseEntity<ForumDTO> newPost(@RequestBody @Valid ForumPost post){
        forumRepository.save(post);
        return ResponseEntity.status(200).body(JsonResponseAdapter.forumDTO(post));
    }

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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePostById(@PathVariable int id){
        if(forumRepository.existsById(id)){
            forumRepository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }

}

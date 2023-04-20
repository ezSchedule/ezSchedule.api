package br.com.ezschedule.apischedule.controller;

import br.com.ezschedule.apischedule.adapter.JsonResponseAdapter;
import br.com.ezschedule.apischedule.model.ForumPost;
import br.com.ezschedule.apischedule.model.DtoClasses.UpdateForumPostForm;
import br.com.ezschedule.apischedule.repository.ForumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("{uri.forum}")
public class ForumController {

    @Autowired
    private ForumRepository forumRepository;

    @GetMapping
    public ResponseEntity<List<ForumPost>> showAllPosts(){
       List<ForumPost> allPosts = forumRepository.findAll();
       if(allPosts.isEmpty()){
           return ResponseEntity.status(204).build();
       }
       return ResponseEntity.status(200).body(allPosts);
    }

    @PostMapping
    public ResponseEntity<ForumPost> newPost(@RequestBody @Valid ForumPost post){
        forumRepository.save(post);
        return ResponseEntity.status(200).body(post);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<ForumPost> updatePostById(@RequestBody @Valid UpdateForumPostForm updatePost,@PathVariable int id){
//        if(forumRepository.findById(id).isPresent()){
//         Optional<ForumPost> oldPost = forumRepository.findById(id);
//         ForumPost updatedPost = JsonResponseAdapter.forumDTO(updatePost,id,oldPost.get().getDateTimePost());
//         forumRepository.save(updatedPost);
//         return ResponseEntity.status(200).body(updatedPost);
//        }
//        return ResponseEntity.status(404).build();
//    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePostById(@PathVariable int id){
        if(forumRepository.existsById(id)){
            forumRepository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }

}

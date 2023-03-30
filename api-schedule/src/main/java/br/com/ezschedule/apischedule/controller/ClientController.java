package br.com.ezschedule.apischedule.controller;

import br.com.ezschedule.apischedule.adapter.JsonResponseAdapter;
import br.com.ezschedule.apischedule.model.Client;
import br.com.ezschedule.apischedule.model.JsonResponse;
import br.com.ezschedule.apischedule.model.UpdatePasswordForm;
import br.com.ezschedule.apischedule.repository.RepositoryAdministrator;
import br.com.ezschedule.apischedule.repository.RepositoryClient;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class ClientController {

    @Autowired
    private RepositoryClient repositoryClient;
    List<Client> listUsers = new ArrayList<>();

    //Show all user's
    @GetMapping
    public ResponseEntity<List<JsonResponse>> showAllUsers() {
        List<JsonResponse> listJsonAnswer = new ArrayList<>();
        if (listUsers.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        for (Client c : listUsers) {
            listJsonAnswer.add(JsonResponseAdapter.Dto(c));
        }
        return ResponseEntity.status(200).body(listJsonAnswer);
    }

    //Register new user
    @PostMapping
    public ResponseEntity<JsonResponse> register(@RequestBody Client newUser) {
        listUsers.add(newUser);
        repositoryClient.save(newUser);
        return ResponseEntity.status(200).body(JsonResponseAdapter.Dto(newUser));
    }

    //login for user
    @PostMapping("/login/{email}/{password}")
    public ResponseEntity<JsonResponse> login(@PathVariable String email, @PathVariable String password) {
        for (Client user : listUsers) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                user.setAuthenticated(true);
                return ResponseEntity.status(200).body(JsonResponseAdapter.Dto(user));
            }
        }
        return ResponseEntity.status(401).build();
    }

    //Delete user by id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> removeById(@PathVariable Integer id) {
        for (Client user : listUsers) {
            if (repositoryClient.existsById(id)){
                this.repositoryClient.deleteById(id);
                return ResponseEntity.status(204).build();
            }
        }
        return ResponseEntity.status(404).build();
    }

    //logout
    @PostMapping("/logout/{email}")
    public ResponseEntity<Void> logout(@PathVariable String email) {
        for (Client user : listUsers) {
            if (user.getEmail().equals(email)) {
                if (user.isAuthenticated()) {
                    user.setAuthenticated(false);
                    return ResponseEntity.status(200).build();
                } else {
                    return ResponseEntity.status(401).build();
                }
            }
        }
        return ResponseEntity.status(404).build();
    }

    @PutMapping
    public ResponseEntity<Client> updatePassword(@RequestBody UpdatePasswordForm updatePasswordForm) {
        for (Client u : listUsers) {
            if (u.getEmail().equals(updatePasswordForm.getEmail()) && u.getPassword().equals(updatePasswordForm.getPassword())) {
                u.setPassword(updatePasswordForm.getNewPassword());
                return ResponseEntity.status(200).build();
            }
        }
        return ResponseEntity.status(404).build();
    }

}

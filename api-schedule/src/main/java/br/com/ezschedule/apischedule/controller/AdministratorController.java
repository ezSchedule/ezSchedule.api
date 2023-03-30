package br.com.ezschedule.apischedule.controller;

import br.com.ezschedule.apischedule.adapter.JsonResponseAdapter;
import br.com.ezschedule.apischedule.model.Administrator;
import br.com.ezschedule.apischedule.model.JsonResponse;
import br.com.ezschedule.apischedule.model.UpdatePasswordForm;
import br.com.ezschedule.apischedule.repository.RepositoryAdministrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/usersAdmin")
public class AdministratorController {

    @Autowired
    private RepositoryAdministrator repositoryAdministrator;

    List<Administrator> listUsers = new ArrayList<>();

    //Show all user's
    @GetMapping
    public ResponseEntity<List<JsonResponse>> showAllUsers() {
        List<JsonResponse> listJsonAnswer = new ArrayList<>();
        if (listUsers.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        for (Administrator a : listUsers) {
            listJsonAnswer.add(JsonResponseAdapter.Dto(a));
        }
        return ResponseEntity.status(200).body(listJsonAnswer);
    }

    //Register new user
    @PostMapping
    public ResponseEntity<JsonResponse> register(@RequestBody Administrator newUser) {
        listUsers.add(newUser);
        repositoryAdministrator.save(newUser);
        return ResponseEntity.status(200).body(JsonResponseAdapter.Dto(newUser));
    }

    //login for user
    @PostMapping("/login/{email}/{password}")
    public ResponseEntity<JsonResponse> login(@PathVariable String email, @PathVariable String password) {
        for (Administrator user : listUsers) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                user.setAuthenticated(true);
                return ResponseEntity.status(200).body(JsonResponseAdapter.Dto(user));
            }
        }
        return ResponseEntity.status(401).build();
    }

    //Delete user by Id
    @DeleteMapping("/delete/{email}")
    public ResponseEntity<Void> removeById(@PathVariable Integer id) {
        if (repositoryAdministrator.existsById(id)){
            repositoryAdministrator.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }

    //logout
    @PostMapping("/logout/{email}")
    public ResponseEntity<Void> logout(@PathVariable String email) {
        for (Administrator user : listUsers) {
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
    public ResponseEntity<Administrator> updatePassword(
            @RequestBody UpdatePasswordForm updatePasswordForm) {
        for (Administrator u : listUsers) {
            if (u.getEmail().equals(updatePasswordForm.getEmail()) && u.getPassword().equals(updatePasswordForm.getPassword())) {
                u.setPassword(updatePasswordForm.getNewPassword());
                return ResponseEntity.status(200).build();
            }
        }
        return ResponseEntity.status(404).build();
    }

}

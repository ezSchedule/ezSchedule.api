package br.com.ezschedule.apischedule.controller;

import br.com.ezschedule.apischedule.adapter.JsonResponseAdapter;
import br.com.ezschedule.apischedule.email.SendMail;
import br.com.ezschedule.apischedule.messages.EmailMessages;
import br.com.ezschedule.apischedule.model.Client;
import br.com.ezschedule.apischedule.model.JsonResponse;
import br.com.ezschedule.apischedule.model.UpdatePasswordForm;
import br.com.ezschedule.apischedule.repository.RepositoryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class ClientController {

    @Autowired
    private RepositoryClient action;
    @Autowired
    private SendMail sendMail;
    List<Client> listUsers = new ArrayList<>();
    private String token = "";

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
        action.save(newUser);
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

    //Delete user by email
    @DeleteMapping("/delete/{email}")
    public ResponseEntity<Void> removeByCpf(@PathVariable String email) {
        for (Client user : listUsers) {
            if (user.getEmail().equals(email)) {
                listUsers.remove(user);
                return ResponseEntity.status(200).build();
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



    @GetMapping("/recovery-password/{email}")
    public ResponseEntity<Void> recoveryPassword(@PathVariable String email){

        boolean exists = action.existsByEmail(email);

        if(exists) {
            Client cliente = null;
            for (int i = 0; i < listUsers.size(); i++) {
                if (email.equals(listUsers.get(i).getEmail())) {
                    cliente = listUsers.get(i);
                }
            }

           this.token = UUID.randomUUID().toString().replace("-", "");
            this.sendMail.send(email, EmailMessages.createTitle(cliente), EmailMessages.messageRecoveryPassword(cliente, this.token));

            return ResponseEntity.status(200).build();
        }

        return ResponseEntity.status(404).build();
    }

    @GetMapping("/input-token/{tokenInput}")
    public ResponseEntity<Void> insertingToken(@PathVariable String tokenInput){
        if(tokenInput.equals(this.token)){
            token = "";
            return ResponseEntity.status(200).build();
        }

        return ResponseEntity.status(404).build();

    }



}

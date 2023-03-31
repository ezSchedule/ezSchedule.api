package br.com.ezschedule.apischedule.controller;

import br.com.ezschedule.apischedule.adapter.JsonResponseAdapter;
import br.com.ezschedule.apischedule.model.Client;
import br.com.ezschedule.apischedule.model.JsonResponse;
import br.com.ezschedule.apischedule.model.UpdatePasswordForm;
import br.com.ezschedule.apischedule.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class ClientController {

    @Autowired
    private ClientRepository repositoryClient;
//    List<Client> listUsers = new ArrayList<>();

    //Show all user's
    @GetMapping
    public ResponseEntity<List<Object>> showAllUsers() {
        List<Object> users = this.repositoryClient.listUserClient();
        if (users.isEmpty()){
            return ResponseEntity.status(204).build();
        }else {
            return ResponseEntity.status(200).body(users);
        }

    }

    //Register new user
    @PostMapping
    public ResponseEntity<JsonResponse> register(@RequestBody Client newUser) {
        this.repositoryClient.save(newUser);
        return ResponseEntity.status(200).body(JsonResponseAdapter.Dto(newUser));
    }

   //login for user
    @PostMapping("/login/{email}/{password}")
    public ResponseEntity<Object> login(@PathVariable String email, @PathVariable String password) {
        Object user = this.repositoryClient.userAuthenticated(email, password);
        if (user.equals(0)){
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.status(200).body(user);
    }

    //Delete user by id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> removeById(@PathVariable Integer id) {
            if (repositoryClient.existsById(id)){
                this.repositoryClient.deleteById(id);
                return ResponseEntity.status(204).build();
            }
        return ResponseEntity.status(404).build();
    }

    //logout
    @PostMapping("/logout/{email}")
    public ResponseEntity<Void> logout(@PathVariable String email) {
        Object user = this.repositoryClient.logoutUser(email);
        if (user.equals(1)) {
            return ResponseEntity.status(200).build();
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @PutMapping
    public ResponseEntity<Object> updatePassword(@RequestBody UpdatePasswordForm updatePasswordForm) {
        if (updatePasswordForm.getPassword().equals(updatePasswordForm.getNewPassword())){
            return ResponseEntity.status(404).build();
        }else {
            Object user = this.repositoryClient.updatePasswordUser(updatePasswordForm.getEmail(), updatePasswordForm.getPassword(), updatePasswordForm.getNewPassword());
            return ResponseEntity.status(200).build();
        }
    }

}

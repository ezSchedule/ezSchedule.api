package br.com.ezschedule.apischedule.controller;

import br.com.ezschedule.apischedule.adapter.JsonResponseAdapter;
import br.com.ezschedule.apischedule.email.SendMail;
import br.com.ezschedule.apischedule.messages.EmailMessages;
import br.com.ezschedule.apischedule.model.Tenant;
import br.com.ezschedule.apischedule.model.JsonResponse;
import br.com.ezschedule.apischedule.model.UpdatePasswordForm;
import br.com.ezschedule.apischedule.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class TenantController {

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private SendMail sendMail;
    List<Tenant> listUsers = new ArrayList<>();
    private String token = "";

    //Show all user's
    @GetMapping
    public ResponseEntity<List<Object>> showAllUsers() {
        List<Object> users = this.tenantRepository.listUserTenant();
        if (users.isEmpty()){
            return ResponseEntity.status(204).build();
        }else {
            return ResponseEntity.status(200).body(users);
        }

    }

    //Register new user
    @PostMapping
    public ResponseEntity<JsonResponse> register(@RequestBody Tenant newUser) {
        this.tenantRepository.save(newUser);
        return ResponseEntity.status(200).body(JsonResponseAdapter.Dto(newUser));
    }

   //login for user
    @PostMapping("/login/{email}/{password}")
    public ResponseEntity<Object> login(@PathVariable String email, @PathVariable String password) {
        Object user = this.tenantRepository.userAuthenticated(email, password);
        if (user.equals(0)){
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.status(200).body(user);
    }

    //Delete user by id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> removeById(@PathVariable Integer id) {
            if (tenantRepository.existsById(id)){
                this.tenantRepository.deleteById(id);
                return ResponseEntity.status(204).build();
            }
        return ResponseEntity.status(404).build();
    }

    //logout
    @PostMapping("/logout/{email}")
    public ResponseEntity<Void> logout(@PathVariable String email) {
        Object user = this.tenantRepository.logoutUser(email);
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
            Object user = this.tenantRepository.updatePasswordUser(updatePasswordForm.getEmail(), updatePasswordForm.getPassword(), updatePasswordForm.getNewPassword());
            return ResponseEntity.status(200).build();
        }
    }

    @GetMapping("/recovery-password/{email}")
    public ResponseEntity<Void> recoveryPassword(@PathVariable String email){

        boolean exists = tenantRepository.existsByEmail(email);

        if(exists) {
            Tenant tenant = null;
            for (int i = 0; i < listUsers.size(); i++) {
                if (email.equals(listUsers.get(i).getEmail())) {
                    tenant = listUsers.get(i);
                }
            }

            this.token = UUID.randomUUID().toString().replace("-", "");
            this.sendMail.send(email, EmailMessages.createTitle(tenant), EmailMessages.messageRecoveryPassword(tenant, this.token));

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

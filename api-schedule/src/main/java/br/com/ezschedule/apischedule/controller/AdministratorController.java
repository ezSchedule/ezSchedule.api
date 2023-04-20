package br.com.ezschedule.apischedule.controller;

import br.com.ezschedule.apischedule.CSV.CsvAdministrator;
import br.com.ezschedule.apischedule.CSV.ListaObj;
import br.com.ezschedule.apischedule.adapter.JsonResponseAdapter;
import br.com.ezschedule.apischedule.model.Administrator;
import br.com.ezschedule.apischedule.model.JsonResponse;
import br.com.ezschedule.apischedule.model.UpdatePasswordForm;
import br.com.ezschedule.apischedule.repository.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usersAdmin")
public class AdministratorController {

    @Autowired
    private AdministratorRepository repositoryAdministrator;

    //Show all user's
    @GetMapping
    public ResponseEntity<List<JsonResponse>> showAllUsers() {
        List<Administrator> users = this.repositoryAdministrator.findAll();
        if (users.isEmpty()){
            return ResponseEntity.status(204).build();
        }else {
            return ResponseEntity.status(200).body(JsonResponseAdapter.convertJsonResponseList(users));
        }

    }

    //Register new user
    @PostMapping
    public ResponseEntity<JsonResponse> register(@RequestBody Administrator newUser) {
        this.repositoryAdministrator.save(newUser);
        return ResponseEntity.status(200).body(JsonResponseAdapter.Dto(newUser));
    }

    //login for user
    @PostMapping("/login")
    public ResponseEntity<JsonResponse> login(@RequestParam String email, @RequestParam String password) {
        Optional<Administrator> user = this.repositoryAdministrator.findByEmail(email);
        if (user.isEmpty()){
            return ResponseEntity.status(404).build();
        }else {
            Administrator userAdmin = user.get();
            return ResponseEntity.status(200).body(JsonResponseAdapter.Dto(userAdmin));
        }

    }

    //Delete user by id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> removeById(@PathVariable Integer id) {
        if (repositoryAdministrator.existsById(id)){
            this.repositoryAdministrator.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }

    //logout
    @PostMapping("/logout/{email}")
    public ResponseEntity<Void> logout(@PathVariable String email) {
        Object user = this.repositoryAdministrator.logoutUser(email);
        if (user.equals(1)) {
            return ResponseEntity.status(200).build();
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @PutMapping
    public ResponseEntity<Administrator> updatePassword(@RequestBody UpdatePasswordForm updatePasswordForm) {
        if (updatePasswordForm.getPassword().equals(updatePasswordForm.getNewPassword())){
            return ResponseEntity.status(404).build();
        }else {
            Administrator user = this.repositoryAdministrator.updatePasswordUser(updatePasswordForm.getEmail(), updatePasswordForm.getPassword(), updatePasswordForm.getNewPassword());
            return ResponseEntity.status(200).build();
        }
    }

    @GetMapping("/gerar-csv-administrator")
    public ResponseEntity<byte[]> gerarCsv(){

        List<Administrator> administrators = repositoryAdministrator.findAll();

        if(!administrators.isEmpty()){

            ListaObj<Administrator> adms = new ListaObj<Administrator>(administrators.size());
            for(Administrator administrator : administrators){
                adms.adiciona(administrator);
            }

            CsvAdministrator.gravaArquivoCsvAdministrador(adms, "administrator");
            return CsvAdministrator.buscarArquivo("administrator");
        }

        return ResponseEntity.status(404).build();
    }

//    public List<JsonResponse> convertJsonResponse(List) {
//        for (int i = 0; i < users.size();i++) {
//            usersNoPassword.add(JsonResponseAdapter.Dto(users.get(i)));
//        }
//        return
//    }



}

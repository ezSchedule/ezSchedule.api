package br.com.ezschedule.apischedule.controller;

import br.com.ezschedule.apischedule.Csv.CsvTenant;
import br.com.ezschedule.apischedule.Csv.ListaObj;
import br.com.ezschedule.apischedule.adapter.JsonResponseAdapter;
import br.com.ezschedule.apischedule.email.SendMail;
import br.com.ezschedule.apischedule.messages.EmailMessages;
import br.com.ezschedule.apischedule.model.Administrator;
import br.com.ezschedule.apischedule.model.DtoClasses.TenantResponse;
import br.com.ezschedule.apischedule.model.Tenant;
import br.com.ezschedule.apischedule.model.DtoClasses.UpdatePasswordForm;
import br.com.ezschedule.apischedule.repository.TenantRepository;
import br.com.ezschedule.apischedule.service.TenantService;
import br.com.ezschedule.apischedule.service.autenticacao.dto.UsuarioLoginDto;
import br.com.ezschedule.apischedule.service.autenticacao.dto.UsuarioTokenDto;
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
    private TenantService tenantService;
    @Autowired
    private SendMail sendMail;
    List<Tenant> listUsers = new ArrayList<>();
    private String token = "";

    //Show all user's
    @GetMapping
    public ResponseEntity<List<TenantResponse>> showAllUsers() {
        List<Tenant> users = this.tenantRepository.findAll();
        if (users.isEmpty()){
            return ResponseEntity.status(204).build();
        }else {
            return ResponseEntity.status(200).body(JsonResponseAdapter.listTenantDTO(users));
        }

    }

    //Register new user
    @PostMapping
    public ResponseEntity<Void> register(@RequestBody Tenant newUser) {
        this.tenantService.criar(newUser);
        return ResponseEntity.status(201).build();
    }

   //login for user
    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDto> login(@RequestBody UsuarioLoginDto usuarioLoginDto) {
        UsuarioTokenDto usuarioTokenDto = this.tenantService.autenticar(usuarioLoginDto);
        return ResponseEntity.status(200).body(usuarioTokenDto);
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

    @GetMapping("/gerar-csv-tenants")
    public ResponseEntity<byte[]> gerarCsv(){
        List<Tenant> tenants = tenantRepository.findAll();
            if(!tenants.isEmpty()){
                ListaObj<Tenant> tenantsReturn = new ListaObj<Tenant>(tenants.size());
                for(Tenant tenant : tenants){
                    tenantsReturn.adiciona(tenant);
                }
                CsvTenant.gravaArquivoCsvTenant(tenantsReturn, "Tenants");
                return CsvTenant.buscarArquivo("Tenants");
            }
            return ResponseEntity.status(404).build();
    }

}

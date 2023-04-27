package br.com.ezschedule.apischedule.controller;

import br.com.ezschedule.apischedule.adapter.JsonResponseAdapter;
import br.com.ezschedule.apischedule.model.Administrator;
import br.com.ezschedule.apischedule.model.DtoClasses.JsonResponse;
import br.com.ezschedule.apischedule.model.DtoClasses.UpdatePasswordForm;
import br.com.ezschedule.apischedule.repository.AdministratorRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Api(value = "Síndico", produces = MediaType.APPLICATION_JSON_VALUE, tags = {"sindico"}, description = "requisições relacionadas a síndicos")
@RestController
@RequestMapping("/usersAdmin")
public class AdministratorController {

    @Autowired
    private AdministratorRepository repositoryAdministrator;

    //Show all user's
    @ApiResponse(responseCode = "204", description =
            "Não há usuários cadastrados.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "usuários encontrados.")
    @ApiOperation(value = "Listar usuários")
    @GetMapping
    public ResponseEntity<List<JsonResponse>> showAllUsers() {
        List<Administrator> users = this.repositoryAdministrator.findAll();
        if (users.isEmpty()){
            return ResponseEntity.status(204).build();
        }else {
            return ResponseEntity.status(200).body(JsonResponseAdapter.convertJsonResponseList(users));
        }

    }

    @ApiResponse(responseCode = "201", description =
            "Usuário cadastrado", content = @Content(schema = @Schema(hidden = true)))
    @ApiOperation(value = "Registro")
    //Register new user
    @PostMapping
    public ResponseEntity<JsonResponse> register(@RequestBody Administrator newUser) {
        this.repositoryAdministrator.save(newUser);
        return ResponseEntity.status(200).body(JsonResponseAdapter.Dto(newUser));
    }

    @ApiResponse(responseCode = "404", description =
            "senha ou email incorretos", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "usuário logado.")
    @ApiOperation(value = "Login")
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
    @ApiResponse(responseCode = "404", description =
            "Usuário não encontrado.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "usuário deletado com sucesso.")
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Remove por id")
    public ResponseEntity<Void> removeById(@PathVariable Integer id) {
        if (repositoryAdministrator.existsById(id)){
            this.repositoryAdministrator.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }

    @ApiResponse(responseCode = "200", description =
            "Logout realizado", content = @Content(schema = @Schema(hidden = true)))
    @ApiOperation(value = "Logout")
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

    @ApiResponse(responseCode = "404", description =
            "senhas iguais", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "senha atualizada")
    @ApiOperation(value = "Atualizar senha")
    @PutMapping
    public ResponseEntity<Administrator> updatePassword(@RequestBody UpdatePasswordForm updatePasswordForm) {
        if (updatePasswordForm.getPassword().equals(updatePasswordForm.getNewPassword())){
            return ResponseEntity.status(404).build();
        }else {
            Administrator user = this.repositoryAdministrator.updatePasswordUser(updatePasswordForm.getEmail(), updatePasswordForm.getPassword(), updatePasswordForm.getNewPassword());
            return ResponseEntity.status(200).build();
        }
    }
}

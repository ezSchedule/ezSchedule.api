package br.com.ezschedule.apischedule.controller;

import br.com.ezschedule.apischedule.adapter.JsonReponseAdapter;
import br.com.ezschedule.apischedule.model.Client;
import br.com.ezschedule.apischedule.model.JsonResponse;
import br.com.ezschedule.apischedule.model.UpdatePasswordForm;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    List<Client> listUsers = new ArrayList<>();

    //Show all user's
    @GetMapping
    public List<JsonResponse> showAllUsers(){
        List<JsonResponse> listJsonAnswer = new ArrayList<>();
         for(Client c: listUsers){
             listJsonAnswer.add(JsonReponseAdapter.Dto(c));
         }
         return listJsonAnswer;
    }

    //Register new user
    @PostMapping
    public JsonResponse register(@RequestBody Client newUser){
         listUsers.add(newUser);
         return JsonReponseAdapter.Dto(newUser);
    }

    //login for user
    @PostMapping("/login/{email}/{password}")
    public JsonResponse login(@PathVariable String email, @PathVariable String password){
        for (Client user : listUsers){
            if (user.getEmail().equals(email) && user.getPassword().equals(password)){
                user.setAuthenticated(true);
                return JsonReponseAdapter.Dto(user);
            }
        }
        return null;
    }

    //Delete user by email
    @DeleteMapping ("/delete/{email}")
    public String removeByCpf(@PathVariable String email){
        for (Client user : listUsers){
            if (user.getEmail().equals(email)){
                listUsers.remove(user);
                return "Usuário deletado com sucesso!!!";
            }
        }
        return "Usuário não foi encontrado";
    }

    //logout
    @PostMapping("/logout/{email}")
    public String logout(@PathVariable String email){
        for (Client user : listUsers){
            if (user.getEmail().equals(email)){
                if(user.isAuthenticated()) {
                    user.setAuthenticated(false);
                    return "Usuário foi deslogado com sucesso!!";
                }
                else{
                    return "Usuário não está autenticado.";
                }
            }
        }
        return "Usuário não foi encontrado\"";
    }

    @PutMapping
    public String updatePassword(
            @RequestBody UpdatePasswordForm updatePasswordForm) {
        for(Client u:listUsers){
            if(u.getEmail().equals(updatePasswordForm.getEmail()) && u.getPassword().equals(updatePasswordForm.getPassword())){
                u.setPassword(updatePasswordForm.getNewPassword());
                return "Senha atualizada com sucesso!!!";
            }
        }
        return "Usuario ou senha estão incorretos!!";
    }

}

package br.com.ezschedule.apischedule;

import br.com.ezschedule.apischedule.model.UserJsonAnswer;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    List<Client> listUsers = new ArrayList<>();

    //Show all user's
    @GetMapping
    public List showAllUsers(){
        return listUsers;
    }

    //Register new user
    @PostMapping
    public UserJsonAnswer register(@RequestBody Client newUser){
         listUsers.add(newUser);
         return new UserJsonAnswer(newUser.getName(), newUser.getPhoneNumber(), newUser.getEmail(), newUser.getCpf(), newUser.isAuthenticated());
    }

    //login for user
    @PostMapping("/login/{email}/{password}")
    public Client login(@PathVariable String email, @PathVariable String password){
        for (Client user : listUsers){
            if (user.getEmail().equals(email) && user.getPassword().equals(password)){
                user.setAuthenticated(true);
                return user;
            }
        }
        return null;
    }

    //Delete user by cpf
    @DeleteMapping ("/delete/{email}")
    public Client removeByCpf(@PathVariable String email){
        for (Client user : listUsers){
            if (user.getEmail().equals(email)){
                listUsers.remove(user);
                return user;
            }
        }
        return null;
    }

    //logout
    @PostMapping("/logout/{email}")
    public Client logout(@PathVariable String email){
        for (Client user : listUsers){
            if (user.getEmail().equals(email)){
                user.setAuthenticated(false);
                return user;
            }
        }
        return null;
    }

}

package br.com.ezschedule.apischedule.controller;

import br.com.ezschedule.apischedule.adapter.JsonResponseAdapter;
import br.com.ezschedule.apischedule.model.Administrator;
import br.com.ezschedule.apischedule.model.Client;
import br.com.ezschedule.apischedule.model.JsonResponse;
import br.com.ezschedule.apischedule.model.UpdatePasswordForm;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class AdminController {

    List<Administrator> listUsers = new ArrayList<>();

    //Show all user's
    @GetMapping
    public List<JsonResponse> showAllUsers() {
        List<JsonResponse> listJsonAnswer = new ArrayList<>();
        for (Administrator a : listUsers) {
            listJsonAnswer.add(JsonResponseAdapter.Dto(a));
        }
        return listJsonAnswer;
    }

    //Register new user
    @PostMapping
    public JsonResponse register(@RequestBody Administrator newUser) {
        listUsers.add(newUser);
        return JsonResponseAdapter.Dto(newUser);
    }

    //login for user
    @PostMapping("/login/{email}/{password}")
    public JsonResponse login(@PathVariable String email, @PathVariable String password) {
        for (Administrator user : listUsers) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                user.setAuthenticated(true);
                return JsonResponseAdapter.Dto(user);
            }
        }
        return null;
    }

    //Delete user by email
    @DeleteMapping("/delete/{email}")
    public String removeByCpf(@PathVariable String email) {
        for (Administrator user : listUsers) {
            if (user.getEmail().equals(email)) {
                listUsers.remove(user);
                return "User deleted successfully!!!";
            }
        }
        return "User not found.";
    }

    //logout
    @PostMapping("/logout/{email}")
    public String logout(@PathVariable String email) {
        for (Administrator user : listUsers) {
            if (user.getEmail().equals(email)) {
                if (user.isAuthenticated()) {
                    user.setAuthenticated(false);
                    return "User has been logged out successfully!!";
                } else {
                    return "User is not authenticated .";
                }
            }
        }
        return "User not found.";
    }

    @PutMapping
    public String updatePassword(
            @RequestBody UpdatePasswordForm updatePasswordForm) {
        for (Administrator u : listUsers) {
            if (u.getEmail().equals(updatePasswordForm.getEmail()) && u.getPassword().equals(updatePasswordForm.getPassword())) {
                u.setPassword(updatePasswordForm.getNewPassword());
                return "Password updated successfully";
            }
        }
        return "User and/or password incorrect";
    }

}

package com.example.devops.controller;

import com.example.devops.DTO.UserDTO;
import com.example.devops.Enum.Role;
import com.example.devops.Service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping
    public String getAllUsers(Model model) {
        List<UserDTO> users = userService.getAllUsersDTO();
        model.addAttribute("users", users);
        return "all-users";
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable UUID id, Model model) {
        UserDTO user = userService.getUserDTOById(id);
        model.addAttribute("user", user);
        return "user";
    }

    @PostMapping("/create")
    public String createUser(@RequestParam String username) {
        userService.createAndSaveUser(username);
        return "redirect:/users";
    }

    @GetMapping("/new")
    public String showCreateUserForm(Model model){
        model.addAttribute("user", new UserDTO());
        return "create-user";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable UUID id){
        userService.deleteUser(id);
        return "redirect:/users";
    }

    @PostMapping("/{id}/role")
    public String updateUserRole(@PathVariable UUID id) {
        userService.setUserRole(id);
        return "redirect:/users";
    }


}

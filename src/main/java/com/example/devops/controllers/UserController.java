package com.example.devops.controllers;

import com.example.devops.models.dtos.UserDTO;
import com.example.devops.services.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("SameReturnValue")
@Controller
@RequestMapping("/users")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


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
    public String createUser(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "create-user";
        }
        userService.createAndSaveUser(userDTO.getEmail());
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

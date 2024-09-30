package com.example.devops.controller;

import com.example.devops.Enum.Role;
import com.example.devops.UserService;
import com.example.devops.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public List<User>getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }


    @PostMapping
    public User createUser(@RequestParam String username) {
        User user = new User();
        user.setUsername(username);
        user.setRole(Role.STUDENT);
        return userService.createUser(user);
    }

    @PutMapping("/{id}/role")
    public User updateUserRole(@PathVariable Long id, @RequestParam Role role) {
        return userService.updateUserRole(id, role);
    }





}

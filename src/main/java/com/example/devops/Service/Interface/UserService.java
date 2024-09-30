package com.example.devops.Service.Interface;

import com.example.devops.Enum.Role;
import com.example.devops.model.User;

import java.util.List;

public interface UserService {


    List<User> getAllUsers();

    User getUserById(Long id);

    User createUser(User user);

    void deleteUser(Long id);

    User updateUserRole(Long userId, Role newRole);
}

package com.example.devops.service;

import com.example.devops.Enum.Role;
import com.example.devops.model.User;
import com.example.devops.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceImplementationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateUser(){
        User newUser = new User();
        newUser.setUsername("Anna Anderson");
        newUser.setRole(Role.STUDENT);



    }


}

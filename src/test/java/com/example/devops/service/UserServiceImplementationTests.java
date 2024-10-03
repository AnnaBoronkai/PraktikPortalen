package com.example.devops.service;

import com.example.devops.DTO.UserDTO;
import com.example.devops.Enum.Role;
import com.example.devops.Service.Interface.UserService;
import com.example.devops.model.User;
import com.example.devops.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
public class UserServiceImplementationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @Test
    @Transactional
    public void testCreateAndSaveUser() {
        String username = "testuser";
        userService.createAndSaveUser(username);

        User savedUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo(username);
        assertThat(savedUser.getRole()).isEqualTo(Role.STUDENT);
    }


    @Test
    @Transactional
    public void testUpdateUserRole(){
        String username = "testuser";
        Role student = Role.STUDENT;
        Role admin = Role.ADMIN;
        User user = new User();
        user.setUsername(username);
        user.setRole(student);
        userRepository.save(user);

        userService.setUserRole(user.getId());
        User updatedUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        assertThat(updatedUser.getRole()).isEqualTo(admin);
        assertThat(updatedUser.getRole()).isNotEqualTo(student);

        userService.setUserRole(updatedUser.getId());
        User revertedUser = userRepository.findById(updatedUser.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        assertThat(revertedUser.getRole()).isEqualTo(student);
        assertThat(updatedUser.getRole()).isNotEqualTo(admin);
    }


    @Test
    @Transactional
    public void testGetAllUsersDTO() {
        User user1 = new User();
        user1.setUsername("testuser1");
        user1.setRole(Role.STUDENT);
        userRepository.save(user1);

        User user2 = new User();
        user2.setUsername("testuser2");
        user2.setRole(Role.ADMIN);
        userRepository.save(user2);

        List<UserDTO> userDTOList = userService.getAllUsersDTO();

        assertThat(userDTOList).hasSize(2);
        assertThat(userDTOList.get(0).getUsername()).isEqualTo("testuser1");
        assertThat(userDTOList.get(1).getUsername()).isEqualTo("testuser2");
        assertThat(userDTOList.get(0).getRole()).isEqualTo(Role.STUDENT);
        assertThat(userDTOList.get(1).getRole()).isEqualTo(Role.ADMIN);
    }


    @Test
    @Transactional
    public void testGetUserDTOById() {
        User user = new User();
        user.setUsername("testuser");
        user.setRole(Role.STUDENT);
        userRepository.save(user);

        UserDTO userDTO = userService.getUserDTOById(user.getId());

        assertThat(userDTO).isNotNull();
        assertThat(userDTO.getUsername()).isEqualTo("testuser");
        assertThat(userDTO.getRole()).isEqualTo(Role.STUDENT);
    }


    @Test
    @Transactional
    public void testGetUserDTOById_NotFound() {
        UUID nonExistingId = UUID.randomUUID();

        assertThrows(RuntimeException.class, () -> {
            userService.getUserDTOById(nonExistingId);
        }, "User not found with ID: " + nonExistingId);
    }


    @Test
    @Transactional
    public void testDeleteUser(){
        User user = new User();
        user.setUsername("testuser");
        user.setRole(Role.STUDENT);
        userRepository.save(user);

        UUID id = user.getId();
        userService.deleteUser(id);
        boolean userExists = userRepository.existsById(id);

        assertThat(userExists).isFalse();
    }


    @Test
    @Transactional
    public void testUserToDTO() {
        UUID id = UUID.randomUUID();
        User user = User.builder()
                .id(id)
                .username("testuser")
                .role(Role.STUDENT)
                .build();

        UserDTO userDTO = userService.userToDTO(user);

        assertThat(userDTO).isNotNull();
        assertThat(userDTO.getId()).isEqualTo(user.getId());
        assertThat(userDTO.getUsername()).isEqualTo(user.getUsername());
        assertThat(userDTO.getRole()).isEqualTo(user.getRole());
    }


    @Test
    @Transactional
    public void testDTOToUser() {
        UUID id = UUID.randomUUID();
        UserDTO userDTO = UserDTO.builder()
                .id(id)
                .username("testuser")
                .role(Role.STUDENT)
                .build();

        User user = userService.DTOToUser(userDTO);

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(userDTO.getId());
        assertThat(user.getUsername()).isEqualTo(userDTO.getUsername());
        assertThat(user.getRole()).isEqualTo(userDTO.getRole());
    }


}

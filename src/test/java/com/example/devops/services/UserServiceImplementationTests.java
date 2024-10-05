package com.example.devops.services;

import com.example.devops.models.dtos.UserDTO;
import com.example.devops.enums.Role;
import com.example.devops.services.interfaces.UserService;
import com.example.devops.models.entities.User;
import com.example.devops.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
        String username = "Andy Almighty";
        userService.createAndSaveUser(username);

        List<User> users = userRepository.findAll();
        User savedUser = users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found"));

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo(username);
        assertThat(savedUser.getRole()).isEqualTo(Role.STUDENT);
    }


    @Test
    @Transactional
    public void testSetUserRole(){
        String username = "Beebe Bright";
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
        user1.setUsername("Charles Checker");
        user1.setRole(Role.STUDENT);
        userRepository.save(user1);

        User user2 = new User();
        user2.setUsername("Daniel Dandy");
        user2.setRole(Role.ADMIN);
        userRepository.save(user2);

        List<UserDTO> userDTOList = userService.getAllUsersDTO();

        assertThat(userDTOList).hasSize(2);
        assertThat(userDTOList.get(0).getUsername()).isEqualTo("Charles Checker");
        assertThat(userDTOList.get(1).getUsername()).isEqualTo("Daniel Dandy");
        assertThat(userDTOList.get(0).getRole()).isEqualTo(Role.STUDENT);
        assertThat(userDTOList.get(1).getRole()).isEqualTo(Role.ADMIN);
    }


    @Test
    @Transactional
    public void testGetUserDTOById() {
        User user = new User();
        user.setUsername("Evelyn Ennis");
        user.setRole(Role.STUDENT);
        userRepository.save(user);

        UserDTO userDTO = userService.getUserDTOById(user.getId());

        assertThat(userDTO).isNotNull();
        assertThat(userDTO.getUsername()).isEqualTo("Evelyn Ennis");
        assertThat(userDTO.getRole()).isEqualTo(Role.STUDENT);
    }


    @Test
    @Transactional
    public void testGetUserDTOById_NotFound() {
        UUID nonExistingId = UUID.randomUUID();

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userService.getUserDTOById(nonExistingId)
        );

        assertEquals("User not found with ID: " + nonExistingId, exception.getMessage());
    }



    @Test
    @Transactional
    public void testDeleteUser(){
        User user = new User();
        user.setUsername("Freddie Ferrari");
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
                .username("Gabby Glorious")
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
                .username("Igor Ignatius")
                .role(Role.STUDENT)
                .build();

        User user = userService.DTOToUser(userDTO);

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(userDTO.getId());
        assertThat(user.getUsername()).isEqualTo(userDTO.getUsername());
        assertThat(user.getRole()).isEqualTo(userDTO.getRole());
    }


}

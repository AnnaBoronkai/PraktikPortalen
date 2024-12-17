package com.example.devops.services.implementations;

import com.example.devops.models.dtos.UserDTO;
import com.example.devops.enums.Role;
import com.example.devops.services.interfaces.UserService;
import com.example.devops.models.entities.User;
import com.example.devops.repositories.UserRepository;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;

    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDTO userToDTO(User user){
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    @Override
    public User DTOToUser(UserDTO userDTO){
        return User.builder()
                .id(userDTO.getId())
                .email(userDTO.getEmail())
                .role(userDTO.getRole())
                .build();
    }

    @Override
    public List<UserDTO> getAllUsersDTO() {
        return userRepository.findAll().stream()
                .map(this::userToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserDTOById(UUID id) {
        return userRepository.findById(id).map(this::userToDTO)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    @Override
    public void createAndSaveUser(String email) {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail(email);
        user.setRole(Role.STUDENT);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public void setUserRole(UUID id) {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (userToUpdate.getRole() == Role.STUDENT){
            userToUpdate.setRole(Role.ADMIN);
        }
        else {
            userToUpdate.setRole(Role.STUDENT);
        }
        userRepository.save(userToUpdate);
    }

    //To-Do Add test for this one
    @Override
    public User loadUser(OidcUser oidcUser) {
        String email = oidcUser.getEmail();
        User user = userRepository.findFirstByEmail(email);
        if (user != null) {
            user.setLastLoginAt(Instant.now());
            return userRepository.save(user);
        } else {
            User newUser = new User();
            newUser.setId(UUID.randomUUID());
            newUser.setEmail(email);
            newUser.setRole(Role.STUDENT);
            return userRepository.save(newUser);
        }
    }

}

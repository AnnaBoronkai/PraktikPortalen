package com.example.devops.Service.implementation;

import com.example.devops.DTO.UserDTO;
import com.example.devops.Enum.Role;
import com.example.devops.Service.Interface.UserService;
import com.example.devops.model.User;
import com.example.devops.repository.UserRepository;
import org.springframework.stereotype.Service;
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
    public User createAndSaveUser(String username) {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername(username);
        user.setRole(Role.STUDENT);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO updateUserRole(UUID id, Role newRole) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(newRole);
        User updatedUser = userRepository.save(user);
        return userToDTO(updatedUser);
    }

    public UserDTO userToDTO(User user){
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }

    public User DTOToUser(UserDTO userDTO){
        return User.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .role(userDTO.getRole())
                .build();
    }






}

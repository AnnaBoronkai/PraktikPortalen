package com.example.devops.Service.implementation;

import com.example.devops.DTO.UserDTO;
import com.example.devops.Enum.Role;
import com.example.devops.Service.Interface.UserService;
import com.example.devops.model.User;
import com.example.devops.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDTO userToDTO(User user){
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }

    @Override
    public User DTOToUser(UserDTO userDTO){
        return User.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
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
    public void createAndSaveUser(String username) {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername(username);
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



}

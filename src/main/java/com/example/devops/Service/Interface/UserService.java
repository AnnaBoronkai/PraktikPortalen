package com.example.devops.Service.Interface;

import com.example.devops.DTO.UserDTO;
import com.example.devops.Enum.Role;
import com.example.devops.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserDTO userToDTO(User user);

    User DTOToUser(UserDTO userDTO);

    List<UserDTO> getAllUsersDTO();

    UserDTO getUserDTOById(UUID id);

    void createAndSaveUser(String username);

    void deleteUser(UUID id);

    void setUserRole(UUID id);


}

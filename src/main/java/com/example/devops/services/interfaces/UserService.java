package com.example.devops.services.interfaces;

import com.example.devops.models.dtos.UserDTO;
import com.example.devops.models.entities.User;
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

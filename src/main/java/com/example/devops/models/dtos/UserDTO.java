package com.example.devops.models.dtos;

import com.example.devops.enums.Role;
import lombok.*;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private UUID id;
    private String username;
    private Role role;
}

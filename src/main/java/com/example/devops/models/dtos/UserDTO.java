package com.example.devops.models.dtos;

import com.example.devops.enums.Role;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private UUID id;
    @NotNull(message = "Email cannot be null")
    @Size(min = 3, max = 20, message = "Username must be between 4 and 20 characters")
    private String email;
    private Role role;
}

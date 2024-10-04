package com.example.devops.repositories;

import com.example.devops.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

@SuppressWarnings("unused")
public interface UserRepository extends JpaRepository<User, UUID> {

}

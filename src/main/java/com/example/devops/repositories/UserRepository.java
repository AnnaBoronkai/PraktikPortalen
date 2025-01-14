package com.example.devops.repositories;

import com.example.devops.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findByEmail(String email);

    User findFirstByEmail(String email);
}

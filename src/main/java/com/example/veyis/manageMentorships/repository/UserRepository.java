package com.example.veyis.manageMentorships.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.veyis.manageMentorships.entity.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);
}
package com.example.veyis.manageMentorships.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.veyis.manageMentorships.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByUsername(String username);
}
package com.example.veyis.service;

import com.example.veyis.manageMentorships.entity.Users;
import com.example.veyis.manageMentorships.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }
}

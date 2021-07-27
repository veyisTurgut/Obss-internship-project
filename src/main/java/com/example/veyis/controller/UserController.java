package com.example.veyis.controller;

import com.example.veyis.manageMentorships.dto.UserDTO;
import com.example.veyis.manageMentorships.entity.Users;
import com.example.veyis.manageMentorships.mapper.UserMapper;
import com.example.veyis.manageMentorships.mapper.UserMapperImpl;
import com.example.veyis.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapperImpl userMapper;

    @GetMapping("/all")
    public List<UserDTO> getAllUsers(){
        List<Users> users = userService.getAllUsers();
        System.out.println(users);
        // ok so far. problem is in mapping
        return userMapper.mapToDto(new ArrayList<>(users));
    }


    @GetMapping("/{id}")
    public UserDTO getAllUsers(@PathVariable int id){
    //TODO: check id is valid.
        List<Users> users = userService.getAllUsers();
        System.out.println(users);
        // ok so far. problem is in mapping
        return userMapper.mapToDto(users.get(id));
    }
}
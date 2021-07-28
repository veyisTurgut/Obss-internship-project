package obss.intern.veyis.controller;

import obss.intern.veyis.manageMentorships.dto.UserDTO;
import obss.intern.veyis.manageMentorships.entity.Program;
import obss.intern.veyis.manageMentorships.entity.Users;
import obss.intern.veyis.manageMentorships.mapper.UserMapperImpl;
import obss.intern.veyis.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @GetMapping("/")
    public String index(){
        SecurityContext context = SecurityContextHolder.getContext();
        System.out.println(context.getAuthentication().getAuthorities());
        return context.getAuthentication().toString();
    }

    @GetMapping("/all")
    public List<UserDTO> getAllUsers() {
        List<Users> users = userService.getAllUsers();
        System.out.println(users);
        return userMapper.mapToDto(new ArrayList<>(users));
    }


    @GetMapping("/{id}")
    public UserDTO getAllUsers(@PathVariable int id) {
        //TODO: check id is valid.
        List<Users> users = userService.getAllUsers();
        System.out.println(users);
        return userMapper.mapToDto(users.get(id));
    }

    @GetMapping("/{user_id}/programsmenteed")
//    public ProgramDTO getProgramsMenteed(@PathVariable int user_id){
    public List<Program> getProgramsMenteed(@PathVariable int user_id) {
        //TODO: check id is valid.
        List<Program> programs = userService.getProgramsMenteed(user_id);
        System.out.println(programs);
        // ok so far. problem is in mapping
        //return ProgramMapper.mapToDto();
        return programs;
    }
}
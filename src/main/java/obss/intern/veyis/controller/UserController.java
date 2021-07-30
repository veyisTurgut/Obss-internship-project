package obss.intern.veyis.controller;

import obss.intern.veyis.config.response.MessageResponse;
import obss.intern.veyis.manageMentorships.dto.ApplicationDTO;
import obss.intern.veyis.manageMentorships.dto.ProgramDTO;
import obss.intern.veyis.manageMentorships.dto.UserDTO;
import obss.intern.veyis.manageMentorships.entity.MentorshipApplication;
import obss.intern.veyis.manageMentorships.entity.Program;
import obss.intern.veyis.manageMentorships.entity.Users;
import obss.intern.veyis.manageMentorships.mapper.ApplicationMapperImpl;
import obss.intern.veyis.manageMentorships.mapper.UserMapperImpl;
import obss.intern.veyis.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapperImpl userMapper;
    private final ApplicationMapperImpl applicationMapper;

    @GetMapping("/")
    public String index(){
        SecurityContext context = SecurityContextHolder.getContext();
        System.out.println(context.getAuthentication().getAuthorities());
        System.out.println(context.getAuthentication().getDetails());
        String details = context.getAuthentication().getDetails().toString();
        String session_id = details.substring(details.indexOf("SessionId")+10,details.indexOf("]"));
        System.out.println(session_id);

        return context.getAuthentication().toString();
    }

    @GetMapping("/all")
    public List<UserDTO> getAllUsers() {
        List<Users> users = userService.getAllUsers();
        System.out.println(users);
        return userMapper.mapToDto(new ArrayList<>(users));
    }


    @GetMapping("/{username}/programsmenteed")
    public List<Program> getProgramsMenteed(@PathVariable String username) {
        //TODO: check id is valid.
        System.out.println(username);
        List<Program> programs = userService.getProgramsMenteed(username);
        System.out.println(programs);
        // ok so far. problem is in mapping
        //return ProgramMapper.mapToDto();
        return programs;
    }

    @GetMapping("/{username}/programMentored")
    public Program getProgramMentored(@PathVariable String username){
        return userService.getProgramMentored(username);
    }

    @GetMapping("/{username}/applications")
    public List<ApplicationDTO> getMentorshipApplications(@PathVariable String username){
        List<MentorshipApplication> applications = userService.getMentorshipApplications(username);
        return (applications == null) ? null : applicationMapper.mapToDto(applications);

    }


}
package obss.intern.veyis.controller;

import obss.intern.veyis.config.response.MessageResponse;
import obss.intern.veyis.manageMentorships.dto.ApplicationDTO;
import obss.intern.veyis.manageMentorships.dto.PhaseDTO;
import obss.intern.veyis.manageMentorships.dto.ProgramDTO;
import obss.intern.veyis.manageMentorships.dto.UserDTO;
import obss.intern.veyis.manageMentorships.entity.MentorshipApplication;
import obss.intern.veyis.manageMentorships.entity.Program;
import obss.intern.veyis.manageMentorships.entity.Users;
import obss.intern.veyis.manageMentorships.mapper.ApplicationMapperImpl;
import obss.intern.veyis.manageMentorships.mapper.ProgramMapperImpl;
import obss.intern.veyis.manageMentorships.mapper.UserMapperImpl;
import obss.intern.veyis.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final ProgramMapperImpl programMapper;

    //@PreAuthorize("hasRole('ADMINS')")
    // or
    @PreAuthorize("hasAuthority('ROLE_ADMINS')")
    @GetMapping("/")//TODO: delete this
    public String index() {
        SecurityContext context = SecurityContextHolder.getContext();
        System.out.println(context.getAuthentication().getAuthorities());
        System.out.println(context.getAuthentication().getDetails());
        String details = context.getAuthentication().getDetails().toString();
        String session_id = details.substring(details.indexOf("SessionId") + 10, details.indexOf("]"));
        System.out.println(session_id);

        return context.getAuthentication().toString();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINS')")
    @GetMapping("/all")//admin
    public List<UserDTO> getAllUsers() {
        List<Users> users = userService.getAllUsers();
        System.out.println(users);
        return userMapper.mapToDto(new ArrayList<>(users));
    }


    @PreAuthorize("hasAnyAuthority('ROLE_ADMINS','ROLE_USERS')")
    @GetMapping("/{username}/programsMenteed")//user-admin
    public List<ProgramDTO> getProgramsMenteed(@PathVariable String username) {
        //TODO: check id is valid.
        System.out.println(username);
        List<Program> programs = userService.getProgramsMenteed(username);
        System.out.println(programs);
        // ok so far. problem is in mapping
        return programMapper.mapToDto(programs);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMINS','ROLE_USERS')")
    @GetMapping("/{username}/programsMentored")//user-admin
    public List<ProgramDTO> getProgramsMentored(@PathVariable String username) {
        return programMapper.mapToDto(userService.getProgramsMentored(username));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMINS','ROLE_USERS')")
    @GetMapping("/{username}/applications")//user-admin
    public List<ApplicationDTO> getMentorshipApplications(@PathVariable String username) {
        /*
        List<MentorshipApplication> applications = userService.getMentorshipApplications(username);
        return (applications == null) ? null : applicationMapper.mapToDto(applications);
        */
        return applicationMapper.mapToDto(userService.getMentorshipApplications(username));

    }
    /*//I decided to calculate these in frontend.
    @GetMapping("/{username}/uncommentedPhases")
    public List<PhaseDTO> getUncommentedPhases(@PathVariable String username){}

    @GetMapping("/{username}/uncommentedPrograms")
    public List<ProgramDTO> getUncommentedPrograms(@PathVariable String username){}
    */
}
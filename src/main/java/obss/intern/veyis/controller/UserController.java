package obss.intern.veyis.controller;

import obss.intern.veyis.manageMentorships.dto.*;
import obss.intern.veyis.manageMentorships.mapper.ApplicationMapperImpl;
import obss.intern.veyis.manageMentorships.mapper.ProgramMapperImpl;
import obss.intern.veyis.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ApplicationMapperImpl applicationMapper;
    private final ProgramMapperImpl programMapper;

    /**
     * <h1> Get Programs Menteed By A User -- Endpoint</h1>
     * This endpoint returns all the programs that menteed by given user.
     *
     * @param username: username of the Mentee
     * @return List<ProgramDTO>: List of program DTOs.
     * @see ProgramDTO
     */
    @PreAuthorize("hasAnyAuthority('ROLE_ADMINS','ROLE_USERS')")
    @GetMapping("/{username}/programsMenteed")//user-admin
    public List<ProgramDTO> getProgramsMenteed(@PathVariable String username) {
        return programMapper.mapToDto(userService.getProgramsMenteed(username));
    }

    /**
     * <h1> Get Programs Mentored By A User -- Endpoint</h1>
     * This endpoint returns all the programs that mentored by given user.
     *
     * @param username: username of the Mentor
     * @return List<ProgramDTO>: List of program DTOs.
     * @see ProgramDTO
     */
    @PreAuthorize("hasAnyAuthority('ROLE_ADMINS','ROLE_USERS')")
    @GetMapping("/{username}/programsMentored")//user-admin
    public List<ProgramDTO> getProgramsMentored(@PathVariable String username) {
        return programMapper.mapToDto(userService.getProgramsMentored(username));
    }

    /**
     * <h1> Get Mentorship Applications Of A User -- Endpoint</h1>
     * This endpoint returns all the mentorship applications of given user.
     *
     * @param username: username of the Mentor
     * @return List<ApplicationDTO>: List of mentorship application DTOs.
     * @see ApplicationDTO
     */
    @PreAuthorize("hasAnyAuthority('ROLE_ADMINS','ROLE_USERS')")
    @GetMapping("/{username}/applications")//user-admin
    public List<ApplicationDTO> getMentorshipApplications(@PathVariable String username) {
        return applicationMapper.mapToDto(userService.getMentorshipApplications(username));
    }

}
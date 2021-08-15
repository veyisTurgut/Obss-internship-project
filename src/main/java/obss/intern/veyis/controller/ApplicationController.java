package obss.intern.veyis.controller;

import lombok.RequiredArgsConstructor;
import obss.intern.veyis.config.response.MessageResponse;
import obss.intern.veyis.config.response.MessageType;
import obss.intern.veyis.manageMentorships.dto.ApplicationDTO;
import obss.intern.veyis.manageMentorships.dto.SubjectDTO;
import obss.intern.veyis.manageMentorships.entity.Subject;
import obss.intern.veyis.manageMentorships.entity.Users;
import obss.intern.veyis.manageMentorships.mapper.ApplicationMapperImpl;
import obss.intern.veyis.manageMentorships.mapper.SubjectMapperImpl;
import obss.intern.veyis.service.ApplicationService;
import obss.intern.veyis.service.SubjectService;
import obss.intern.veyis.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpHeaders;
import java.util.Dictionary;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final SubjectService subjectService;
    private final UserService userService;
    private final ApplicationMapperImpl applicationMapper;
    private final SubjectMapperImpl subjectMapper;

    @PreAuthorize("hasAuthority('ROLE_USERS')")
    @PostMapping("/")//user
    public MessageResponse applyForMentorship(@RequestBody @Validated ApplicationDTO applicationDTO) {
        Subject subject = subjectService.getByKeys(applicationDTO.getSubject_name(), applicationDTO.getSubsubject_name());
        Users mentor_applicant = userService.getUser(applicationDTO.getApplicant_username());
        return applicationService.addMentorshipApplication(applicationMapper.mapToEntity(applicationDTO, subject, mentor_applicant));
    }

    @PreAuthorize("hasAuthority('ROLE_USERS')")
    @DeleteMapping("/")//user
    public MessageResponse deleteMentorshipApplication(@RequestBody @Validated ApplicationDTO applicationDTO) {
        Subject subject = subjectService.getByKeys(applicationDTO.getSubject_name(), applicationDTO.getSubsubject_name());
        Users mentor_applicant = userService.getUser(applicationDTO.getApplicant_username());
        if (mentor_applicant == null || subject == null)
            return new MessageResponse("Hatalı parametreler!", MessageType.ERROR);
        return applicationService.deleteMentorshipApplication(subject, mentor_applicant);
    }


    @PreAuthorize("hasAuthority('ROLE_ADMINS')")
    @GetMapping("/all")//admin
    public List<ApplicationDTO> allApplications() {
        return applicationMapper.mapToDto(applicationService.findAllApplications());
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINS')")
    @GetMapping("/approved")//admin
    public List<ApplicationDTO> approvedApplications() {
        return applicationMapper.mapToDto(applicationService.findApprovedApplications());
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINS')")
    @GetMapping("/open")//admin
    public List<ApplicationDTO> openApplications() {
        return applicationMapper.mapToDto(applicationService.findOpenApplications());
    }

    @PreAuthorize("hasAuthority('ROLE_USERS')")
    @GetMapping("/{username}/can")//user
    public List<ApplicationDTO> applicationsUserCanApply(@PathVariable String username) {
        System.out.println(username);
        System.out.println(applicationMapper.mapToDto(applicationService.findSubjectsUserCanApply(username)));
        return applicationMapper.mapToDto(applicationService.findSubjectsUserCanApply(username));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINS')")
    @PutMapping("/reject")//admin
    //may change this such that I would only get application_id rather than DTO. will decide after frontend
    public MessageResponse rejectMentorshipApplication(@RequestBody @Validated ApplicationDTO applicationDTO) {
        return applicationService.rejectMentorshipApplication(applicationDTO);
    }

    @PreAuthorize("hasAuthority('ROLE_USERS')")
    @PutMapping("/update")//user(mentor)
    //may change this such that I would only get application_id rather than DTO. will decide after frontend
    public MessageResponse updateMentorshipApplication(@RequestBody @Validated ApplicationDTO applicationDTO) {
        return applicationService.updateMentorshipApplication(applicationDTO);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINS')")
    @PutMapping("/approve")//admin
    //may change this such that I would only get application_id rather than DTO. will decide after frontend
    public MessageResponse approveMentorshipApplication(@RequestBody @Validated ApplicationDTO applicationDTO) {
        return applicationService.approveMentorshipApplication(applicationDTO);
    }

    @PreAuthorize("hasAuthority('ROLE_USERS')")
    @PostMapping("/search/{keyword}")
    public List<ApplicationDTO> findByKeyword(@PathVariable String keyword, @RequestBody List<SubjectDTO> subjects) {
        return applicationMapper.mapToDto(applicationService.findByKeyword(keyword, subjects));
    }

}

package obss.intern.veyis.controller;

import lombok.RequiredArgsConstructor;
import obss.intern.veyis.config.response.MessageResponse;
import obss.intern.veyis.manageMentorships.dto.ApplicationDTO;
import obss.intern.veyis.manageMentorships.entity.Subject;
import obss.intern.veyis.manageMentorships.entity.Users;
import obss.intern.veyis.manageMentorships.mapper.ApplicationMapperImpl;
import obss.intern.veyis.service.ApplicationService;
import obss.intern.veyis.service.SubjectService;
import obss.intern.veyis.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/")//user
    public MessageResponse applyForMentorship(@RequestBody @Validated ApplicationDTO applicationDTO) {
        Subject subject = subjectService.getByKeys(applicationDTO.getSubject_name(), applicationDTO.getSubsubject_name());
        Users mentor_applicant = userService.getUser(applicationDTO.getApplicant_username());
        return applicationService.addMentorshipApplication(applicationMapper.mapToEntity(applicationDTO, subject, mentor_applicant));
    }

    @GetMapping("/all")//admin
    public List<ApplicationDTO> allApplications() {
        return applicationMapper.mapToDto(applicationService.findAllApplications());
    }

    @GetMapping("/approved")//user(mentee)-admin
    public List<ApplicationDTO> approvedApplications() {
        return applicationMapper.mapToDto(applicationService.findApprovedApplications());
    }

    @GetMapping("/open")//admin
    public List<ApplicationDTO> openApplications() {
        return applicationMapper.mapToDto(applicationService.findOpenApplications());
    }

    @PutMapping("/reject")//admin
    //may change this such that I would only get application_id rather than DTO. will decide after frontend
    public MessageResponse rejectMentorshipApplication(@RequestBody @Validated ApplicationDTO applicationDTO) {
        return applicationService.rejectMentorshipApplication(applicationDTO);
    }

    @PutMapping("/update")//user(mentor)
    //may change this such that I would only get application_id rather than DTO. will decide after frontend
    public MessageResponse updateMentorshipApplication(@RequestBody @Validated ApplicationDTO applicationDTO) {
        return applicationService.updateMentorshipApplication(applicationDTO);
    }

    @PutMapping("/approve")//admin
    //may change this such that I would only get application_id rather than DTO. will decide after frontend
    public MessageResponse approveMentorshipApplication(@RequestBody @Validated ApplicationDTO applicationDTO) {
        return applicationService.approveMentorshipApplication(applicationDTO);
    }

    @GetMapping("/{keyword}")
    public List<ApplicationDTO> findByKeyword(@PathVariable String keyword) {
        return applicationMapper.mapToDto(applicationService.findByKeyword(keyword));
    }

}

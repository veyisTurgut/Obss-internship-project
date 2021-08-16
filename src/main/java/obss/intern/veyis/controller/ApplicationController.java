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


    /**
     * <h1> Apply For a Mentorship As a Mentor -- Endpoint</h1>
     * Users supply a "valid" Application DTO in the body of the request to apply to be a mentor.
     * This endpoint is just an ambassador, it maps the DTO object to an Entity object and
     * calls the "addMentorshipApplication" function in "ApplicationService" class.
     *
     * @param applicationDTO: Contains the information of applicant username, subject and experience of the applicant upon subject.
     * @return MessageResponse: Returns "SUCCESS" after successful operation or "ERROR" with a reason.
     * @see ApplicationDTO
     * @see MessageResponse
     */
    @PreAuthorize("hasAuthority('ROLE_USERS')")
    @PostMapping("/")//user
    public MessageResponse applyForMentorship(@RequestBody @Validated ApplicationDTO applicationDTO) {
        Subject subject = subjectService.getByKeys(applicationDTO.getSubject_name(), applicationDTO.getSubsubject_name());
        Users mentor_applicant = userService.getUser(applicationDTO.getApplicant_username());
        return applicationService.addMentorshipApplication(applicationMapper.mapToEntity(applicationDTO, subject, mentor_applicant));
    }

    /**
     * <h1> Delete a Mentorship As a Mentor -- Endpoint</h1>
     * Users supply a "valid" Application DTO in the body of the request to delete previous mentorship application.
     * <br/>This endpoint is just an ambassador, it calls the "deleteMentorshipApplication" function in "ApplicationService" class.
     *
     * @param applicationDTO: Contains the information of applicant username and subject of the applicant upon subject.
     * @return MessageResponse: Returns "SUCCESS" after successful operation or "ERROR" with a reason.
     * @see ApplicationDTO
     * @see MessageResponse
     */
    @PreAuthorize("hasAuthority('ROLE_USERS')")
    @DeleteMapping("/")//user
    public MessageResponse deleteMentorshipApplication(@RequestBody @Validated ApplicationDTO applicationDTO) {
        Subject subject = subjectService.getByKeys(applicationDTO.getSubject_name(), applicationDTO.getSubsubject_name());
        Users mentor_applicant = userService.getUser(applicationDTO.getApplicant_username());
        return applicationService.deleteMentorshipApplication(subject, mentor_applicant);
    }

    /**
     * <h1> List All Mentorship Applications As an Admin -- Endpoint</h1>
     * Admins can see all the mentorship applications in the system as a list.
     * It maps MentorshipApplication objects returned from service class to DTO's.
     *
     * @return List<ApplicationDTO>: Returns all the mentorship applications as a list.
     * @see ApplicationDTO
     */
    @PreAuthorize("hasAuthority('ROLE_ADMINS')")
    @GetMapping("/all")//admin
    public List<ApplicationDTO> allApplications() {
        return applicationMapper.mapToDto(applicationService.findAllApplications());
    }

    /**
     * <h1> Filter out Mentorship Applications By Their Status And List Them As an Admin -- Endpoint</h1>
     * Admins can filter out the mentorship applications in the system by their status and see them as a list.
     * It maps MentorshipApplication objects returned from service class to DTO's.
     *
     * @param status: status of the application. May be one of the following: open - approved - rejected - full
     * @return List<ApplicationDTO>: Returns the mentorship applications whose status equals to the param as a list.
     * @see ApplicationDTO
     */
    @PreAuthorize("hasAuthority('ROLE_ADMINS')")
    @GetMapping("/")//admin
    public List<ApplicationDTO> applicationsByStatus(@RequestParam String status) {
        return applicationMapper.mapToDto(applicationService.findApplicationsByStatus(status));
    }

    /**
     * <h1> List All Mentorship Applications One Can Enroll As a Mentee -- Endpoint</h1>
     * Users can see all the subjects and their mentors they can enroll.
     * <br/>
     * This endpoint calls the "findSubjectsUserCanEnrollAsAMentee" function of service class
     * and maps the returned value from there.
     *
     * @param username Username of the mentee.
     * @return List<ApplicationDTO>: Returns the subjects and mentors offered that mentee can enroll .
     * @see ApplicationDTO
     */
    @PreAuthorize("hasAuthority('ROLE_USERS')")
    @GetMapping("/{username}/can")//user
    public List<ApplicationDTO> subjectsUserCanEnrollAsAMentee(@PathVariable String username) {
        return applicationMapper.mapToDto(applicationService.findSubjectsUserCanEnrollAsAMentee(username));
    }

    /**
     * <h1> Approve a Mentorship Application As an Admin -- Endpoint</h1>
     * Admins can approve an application if they think mentor applicant is suitable for the purpose.
     * <br/>
     * This endpoint acts as an ambassador and returns the "approveMentorshipApplication" function of service class.
     *
     * @param applicationDTO: Information of the application.
     * @return MessageResponse: SUCCESS upon successful operation, ERROR with an explanation otherwise.
     * @see ApplicationDTO
     */
    @PreAuthorize("hasAuthority('ROLE_ADMINS')")
    @PutMapping("/approve")//admin
    public MessageResponse approveMentorshipApplication(@RequestBody @Validated ApplicationDTO applicationDTO) {
        return applicationService.approveMentorshipApplication(applicationDTO);
    }

    /**
     * <h1> Reject a Mentorship Application As an Admin -- Endpoint</h1>
     * Admins can reject an application if they think mentor applicant is not capable enough.
     * <br/>
     * This endpoint acts as an ambassador and returns the "rejectMentorshipApplication" function of service class.
     *
     * @param applicationDTO: Information of the application.
     * @return MessageResponse: SUCCESS upon successful operation, ERROR with an explanation otherwise.
     * @see ApplicationDTO
     */
    @PreAuthorize("hasAuthority('ROLE_ADMINS')")
    @PutMapping("/reject")//admin
    public MessageResponse rejectMentorshipApplication(@RequestBody ApplicationDTO applicationDTO) {
        return applicationService.rejectMentorshipApplication(applicationDTO);
    }

    /**
     * <h1> Update a Mentorship Application Experience As a User -- Endpoint</h1>
     * Users can update the experience section of their application.
     * <br/>
     * This endpoint acts as an ambassador and returns the "updateMentorshipApplication" function of service class.
     *
     * @param applicationDTO: Information of the application.
     * @return MessageResponse: SUCCESS upon successful operation, ERROR with an explanation otherwise.
     * @see ApplicationDTO
     */
    @PreAuthorize("hasAuthority('ROLE_USERS')")
    @PutMapping("/update")//user(mentor)
    public MessageResponse updateMentorshipApplication(@RequestBody @Validated ApplicationDTO applicationDTO) {
        return applicationService.updateMentorshipApplication(applicationDTO);
    }

/*
    @PreAuthorize("hasAuthority('ROLE_USERS')")
    @PostMapping("/search/{keyword}")
    public List<ApplicationDTO> findByKeyword(@PathVariable String keyword, @RequestBody List<SubjectDTO> subjects) {
        return applicationMapper.mapToDto(applicationService.findByKeyword(keyword, subjects));
    }
*/
}

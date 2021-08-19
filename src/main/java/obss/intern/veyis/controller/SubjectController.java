package obss.intern.veyis.controller;

import lombok.RequiredArgsConstructor;
import obss.intern.veyis.config.response.MessageResponse;
import obss.intern.veyis.manageMentorships.dto.ApplicationDTO;
import obss.intern.veyis.manageMentorships.dto.SubjectDTO;
import obss.intern.veyis.manageMentorships.mapper.ApplicationMapperImpl;
import obss.intern.veyis.manageMentorships.mapper.SubjectMapperImpl;
import obss.intern.veyis.service.SubjectService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService subjectService;
    private final SubjectMapperImpl subjectMapper;
    private final ApplicationMapperImpl applicationMapper;

    /**
     * <h1> Get All Subjects -- Endpoint</h1>
     * This endpoint returns all the subjects in the system.
     *
     * @return List<SubjectDTO>: List of subject DTOs.
     * @see SubjectDTO
     */
    @PreAuthorize("hasAnyAuthority('ROLE_ADMINS','ROLE_USERS')")
    @GetMapping("/all")//admin-user
    public List<SubjectDTO> getAllSubjects() {
        return subjectMapper.mapToDto(subjectService.getAllSubjects());
    }

    /**
     * <h1> Get All Subjects That A User Applied -- Endpoint</h1>
     * This endpoint returns all the subjects that given user applied to be a mentor in the system.
     *
     * @param username: username of the user.
     * @return List<SubjectDTO>: List of subject DTOs.
     * @see SubjectDTO
     */
    @PreAuthorize("hasAnyAuthority('ROLE_ADMINS','ROLE_USERS')")
    @GetMapping("/{username}")//admin-user
    public List<ApplicationDTO> getSubjectsThatAUserApplied(@PathVariable String username) {
        return applicationMapper.mapToDto(subjectService.getSubjectsThatAUserApplied(username));
    }

    /**
     * <h1> Get All Subjects That A User Not Applied -- Endpoint</h1>
     * This endpoint returns all the subjects that given user NOT applied to be a mentor in the system.
     *
     * @param username: username of the user.
     * @return List<SubjectDTO>: List of subject DTOs.
     * @see SubjectDTO
     */
    @PreAuthorize("hasAnyAuthority('ROLE_ADMINS','ROLE_USERS')")
    @GetMapping("/except/{username}")//admin-user
    public List<SubjectDTO> getAllSubjectsExceptAUSer(@PathVariable String username) {
        return subjectMapper.mapToDto(subjectService.getAllSubjectsExceptAUser(username));
    }

    /**
     * <h1> Create a Subject As an Admin -- Endpoint</h1>
     * This endpoint creates a Subject Entity from given DTO and calls service class.
     *
     * @param subjectDTO: Contents of the subject.
     * @return MessageResponse: SUCCESS upon successful operation, ERROR with an explanation otherwise.
     */
    @PreAuthorize("hasAuthority('ROLE_ADMINS')")
    @PostMapping("/")//admin
    public MessageResponse addSubject(@RequestBody @Validated SubjectDTO subjectDTO) {
        return subjectService.addSubject(subjectMapper.mapToEntity(subjectDTO));
    }

    /**
     * <h1> Delete a Subject By ID As an Admin -- Endpoint</h1>
     * This endpoint calls service class to delete the Subject with the given ID.
     *
     * @param subject_id: ID of the subject
     * @return MessageResponse: SUCCESS upon successful operation, ERROR with an explanation otherwise.
     */
    @PreAuthorize("hasAuthority('ROLE_ADMINS')")
    @DeleteMapping("/{subject_id}")//admin
    public MessageResponse deleteSubjectById(@PathVariable Long subject_id) {
        return subjectService.deleteSubjectById(subject_id);
    }

}

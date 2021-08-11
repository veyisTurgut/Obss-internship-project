package obss.intern.veyis.controller;

import lombok.RequiredArgsConstructor;
import obss.intern.veyis.config.response.MessageResponse;
import obss.intern.veyis.manageMentorships.dto.SubjectDTO;
import obss.intern.veyis.manageMentorships.mapper.SubjectMapper;
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

    @PreAuthorize("hasAnyAuthority('ROLE_ADMINS','ROLE_USERS')")
    @GetMapping("/all")//admin-user
    public List<SubjectDTO> getAllSubjects() {
        return subjectMapper.mapToDto(subjectService.getAllSubjects());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMINS','ROLE_USERS')")
    @GetMapping("/{username}")//admin-user
    public List<SubjectDTO> getApplicationsOfAUser(@PathVariable String username) {
        return subjectMapper.mapToDto(subjectService.getApplicationsOfAUser(username));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMINS','ROLE_USERS')")
    @GetMapping("/except/{username}")//admin-user
    public List<SubjectDTO> getAllSubjectsExceptAUSer(@PathVariable String username) {
        return subjectMapper.mapToDto(subjectService.getAllSubjectsExceptAUser(username));
    }


    @PreAuthorize("hasAuthority('ROLE_ADMINS')")
    @PostMapping("/")//admin
    public MessageResponse addSubject(@RequestBody @Validated SubjectDTO subjectDTO) {
        return subjectService.addSubject(subjectMapper.mapToEntity(subjectDTO));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINS')")
    @DeleteMapping("/")//admin
    public MessageResponse deleteSubject(@RequestBody @Validated SubjectDTO subjectDTO) {
        return subjectService.deleteSubject(subjectDTO);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMINS')")
    @DeleteMapping("/{subject_id}")//admin
    public MessageResponse deleteSubjectById(@PathVariable Long subject_id) {
        return subjectService.deleteSubjectById(subject_id);
    }
}

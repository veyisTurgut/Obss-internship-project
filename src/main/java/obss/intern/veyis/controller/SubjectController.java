package obss.intern.veyis.controller;

import lombok.RequiredArgsConstructor;
import obss.intern.veyis.config.response.MessageResponse;
import obss.intern.veyis.manageMentorships.dto.SubjectDTO;
import obss.intern.veyis.manageMentorships.mapper.SubjectMapper;
import obss.intern.veyis.service.SubjectService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService subjectService;
    private final SubjectMapper subjectMapper;
    @GetMapping("/all")
    public List<SubjectDTO> getAllSubjects(){
        return subjectMapper.mapToDto(subjectService.getAllSubjects());
    }

    @PostMapping()
    public MessageResponse addSubject(@RequestBody SubjectDTO subjectDTO){
        return subjectService.addSubject(subjectMapper.mapToEntity(subjectDTO));
    }

    @DeleteMapping()
    public MessageResponse deleteSubject(@RequestBody SubjectDTO subjectDTO){
        return subjectService.deleteSubject(subjectDTO);
    }
}

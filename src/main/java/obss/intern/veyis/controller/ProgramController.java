package obss.intern.veyis.controller;

import lombok.RequiredArgsConstructor;
import obss.intern.veyis.config.response.MessageResponse;
import obss.intern.veyis.manageMentorships.dto.PhaseDTO;
import obss.intern.veyis.manageMentorships.dto.ProgramDTO;
import obss.intern.veyis.manageMentorships.entity.Phase;
import obss.intern.veyis.manageMentorships.entity.Subject;
import obss.intern.veyis.manageMentorships.entity.Users;
import obss.intern.veyis.manageMentorships.entity.compositeKeys.ProgramId;
import obss.intern.veyis.manageMentorships.mapper.PhaseMapperImpl;
import obss.intern.veyis.manageMentorships.mapper.ProgramMapperImpl;
import obss.intern.veyis.service.ProgramService;
import obss.intern.veyis.service.SubjectService;
import obss.intern.veyis.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/programs")
public class ProgramController {

    private final ProgramMapperImpl programMapper;
    private final PhaseMapperImpl phaseMapper;
    private final ProgramService programService;
    private final UserService userService;
    private final SubjectService subjectService;

    @GetMapping("/all")
    public List<ProgramDTO> getAllPrograms() {
        return programMapper.mapToDto(programService.getAllPrograms());
    }

    @GetMapping("/{program_id}")
    public ProgramDTO getAProgramById(@PathVariable Long program_id) {
        return programMapper.mapToDto(programService.getById(program_id));
    }

    @GetMapping("/{mentee_username}/{mentor_username}/{subject_name}/{subsubject_name}")
    public ProgramDTO getAProgramByKeys(@PathVariable String mentee_username, @PathVariable String mentor_username, @PathVariable String subject_name, @PathVariable String subsubject_name) {
        return programMapper.mapToDto(programService.getByKeys(mentee_username, mentor_username, subject_name, subsubject_name));
    }

    @PutMapping("/{program_id}/updatePhase")
    public MessageResponse updatePhases(@PathVariable Long program_id, @RequestBody @Validated PhaseDTO phaseDTO) {
        return programService.updatePhase(program_id, phaseMapper.mapToEntity(phaseDTO));
    }

    @PutMapping("/{program_id}/closePhase")
    public MessageResponse closePhase(@PathVariable Long program_id, @RequestBody @Validated PhaseDTO phaseDTO) {
        return programService.closePhase(program_id, phaseMapper.mapToEntity(phaseDTO));
    }

    @PostMapping()
    public MessageResponse addProgram(@RequestBody @Validated ProgramDTO programDTO) {
        Users Mentor = userService.getUser(programDTO.getMentor_username());
        Users Mentee = userService.getUser(programDTO.getMentee_username());
        Set<Phase> phases = phaseMapper.mapToEntity(programDTO.getPhases().stream().collect(Collectors.toList())).stream().collect(Collectors.toSet());
        Subject subject = subjectService.getByKeys(programDTO.getSubject_name(), programDTO.getSubsubject_name());

        return programService.addProgram(programMapper.mapToEntity(programDTO, Mentor, Mentee, phases, subject));
    }


}

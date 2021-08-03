package obss.intern.veyis.controller;

import lombok.RequiredArgsConstructor;
import obss.intern.veyis.config.response.MessageResponse;
import obss.intern.veyis.config.response.MessageType;
import obss.intern.veyis.manageMentorships.dto.PhaseDTO;
import obss.intern.veyis.manageMentorships.dto.ProgramDTO;
import obss.intern.veyis.manageMentorships.dto.UserDTO;
import obss.intern.veyis.manageMentorships.entity.Phase;
import obss.intern.veyis.manageMentorships.entity.Program;
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

    @GetMapping("/all")//admin
    public List<ProgramDTO> getAllPrograms() {
        return programMapper.mapToDto(programService.getAllPrograms());
    }

    @GetMapping("/active")//user
    public List<ProgramDTO> getActivePrograms() {
        return programMapper.mapToDto(programService.getActivePrograms());
    }


    @GetMapping("/{program_id}")//admin-user
    public ProgramDTO getAProgramById(@PathVariable Long program_id) {
        return programMapper.mapToDto(programService.getById(program_id));
    }

    @GetMapping("/{mentee_username}/{mentor_username}/{subject_name}/{subsubject_name}")//admin-user
    public ProgramDTO getAProgramByKeys(@PathVariable String mentee_username, @PathVariable String mentor_username, @PathVariable String subject_name, @PathVariable String subsubject_name) {
        return programMapper.mapToDto(programService.getByKeys(mentee_username, mentor_username, subject_name, subsubject_name));
    }

    @PutMapping("/{program_id}/updatePhase")//user
    public MessageResponse updatePhases(@PathVariable Long program_id, @RequestBody @Validated PhaseDTO phaseDTO) {
        Program program = programService.getById(program_id);
        if (program == null) return new MessageResponse("Böyle bir program yok.", MessageType.ERROR);
        return programService.updatePhase(program_id, phaseMapper.mapToEntity(phaseDTO, program));
    }

    @PutMapping("/{program_id}/closePhase")//user
    public MessageResponse closePhase(@PathVariable Long program_id, @RequestBody @Validated PhaseDTO phaseDTO) {
        Program program = programService.getById(program_id);
        if (program == null) return new MessageResponse("Böyle bir program yok.", MessageType.ERROR);
        return programService.closePhase(program_id, phaseMapper.mapToEntity(phaseDTO, program));
    }

    @PostMapping()//user - this is called by mentee to enroll.
    public MessageResponse addProgram(@RequestBody @Validated ProgramDTO programDTO) {
        programDTO.setProgram_id(programService.getMax() + 1);
        Users mentor = userService.getUser(programDTO.getMentor_username());
        Users mentee = userService.getUser(programDTO.getMentee_username());
        if (mentor == null || mentee == null)
            return new MessageResponse("Mentor ya da mentee sistemde kayıtlı değil.", MessageType.ERROR);
        Subject subject = subjectService.getByKeys(programDTO.getSubject_name(), programDTO.getSubsubject_name());
        if (subject == null) subjectService.addSubject(programDTO.getSubject_name(), programDTO.getSubsubject_name());
        Program program = programMapper.mapToEntity(programDTO, mentor, mentee, subject);

        //Set<Phase> phases = phaseMapper.mapToEntity(programDTO.getPhases().stream().collect(Collectors.toList()), program).stream().collect(Collectors.toSet());
        //program.setPhases(phases);
        return programService.addProgram(program);
    }


    @PostMapping("/{program_id}/phases")//user
    public MessageResponse addPhases(@PathVariable Long program_id, @RequestBody @Validated List<PhaseDTO> phaseDTOList) {
        Program program = programService.getById(program_id);
        if (program == null) return new MessageResponse("Böyle bir program yok.", MessageType.ERROR);
        Set<Phase> phases = phaseMapper.mapToEntity(phaseDTOList, program).stream().collect(Collectors.toSet());
        return programService.addPhases(program, phases);
    }


    @PutMapping("{program_id}")//user
    public MessageResponse enrollMentee(@PathVariable Long program_id, @RequestBody @Validated String mentee_username) {
        return programService.addMentee(program_id, mentee_username);
    }

}

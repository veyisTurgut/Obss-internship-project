package obss.intern.veyis.controller;

import lombok.RequiredArgsConstructor;
import obss.intern.veyis.config.response.MessageResponse;
import obss.intern.veyis.manageMentorships.dto.PhaseDTO;
import obss.intern.veyis.manageMentorships.dto.ProgramDTO;
import obss.intern.veyis.manageMentorships.entity.Phase;
import obss.intern.veyis.manageMentorships.entity.Program;
import obss.intern.veyis.manageMentorships.mapper.PhaseMapper;
import obss.intern.veyis.manageMentorships.mapper.ProgramMapper;
import obss.intern.veyis.service.ProgramService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/programs")
public class ProgramController {

    private final ProgramMapper programMapper;
    private final PhaseMapper phaseMapper;
    private final ProgramService programService;


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
        return programService.addProgram(programMapper.mapToEntity(programDTO));
    }


}

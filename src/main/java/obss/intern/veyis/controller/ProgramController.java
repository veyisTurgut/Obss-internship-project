package obss.intern.veyis.controller;

import lombok.RequiredArgsConstructor;
import obss.intern.veyis.config.response.MessageResponse;
import obss.intern.veyis.config.response.MessageType;
import obss.intern.veyis.manageMentorships.dto.PhaseDTO;
import obss.intern.veyis.manageMentorships.dto.ProgramDTO;
import obss.intern.veyis.manageMentorships.entity.Program;
import obss.intern.veyis.manageMentorships.entity.Subject;
import obss.intern.veyis.manageMentorships.entity.Users;
import obss.intern.veyis.manageMentorships.mapper.ProgramMapperImpl;
import obss.intern.veyis.service.ProgramService;
import obss.intern.veyis.service.SubjectService;
import obss.intern.veyis.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/programs")
public class ProgramController {

    private final ProgramMapperImpl programMapper;
    private final ProgramService programService;
    private final UserService userService;
    private final SubjectService subjectService;

    /**
     * <h1> Get All Programs As an Admin-- Endpoint</h1>
     * Admins can see all the programs.
     * <br/>
     * This endpoint acts as an ambassador and maps the returned value of "getAllPrograms" function of service class.
     *
     * @return List<ProgramDTO>: List of Program DTO's.
     * @see ProgramDTO
     */
    @PreAuthorize("hasAuthority('ROLE_ADMINS')")
    @GetMapping("/all")//admin
    public List<ProgramDTO> getAllPrograms() {
        return programMapper.mapToDto(programService.getAllPrograms());
    }

    /**
     * <h1> Get a Program By ID -- Endpoint</h1>
     * By this endpoint, users and admins can get the information of a program upon supplying its id.
     * This endpoint acts as an ambassador and maps the returned value of "getById" function of service class.
     *
     * @param program_id Id of the program.
     * @return ProgramDTO
     */
    @PreAuthorize("hasAnyAuthority('ROLE_ADMINS','ROLE_USERS')")
    @GetMapping("/{program_id}")//admin-user
    public ProgramDTO getAProgramById(@PathVariable Long program_id) {
        return programMapper.mapToDto(programService.getById(program_id));
    }

    /**
     * <h1> Add a Program As a Mentee -- Endpoint</h1>
     * By this endpoint, users can enroll a program as a mentee.
     * <br/>
     * It first fetches the objects of mentor, mentee and subject.
     * Then creates a new program using these objects, yet some of them may be null.
     *
     * @param programDTO Contents of the program.
     * @return MessageResponse: SUCCESS upon successful operation, ERROR with an explanation otherwise.
     */
    @PreAuthorize("hasAuthority('ROLE_USERS')")
    @PostMapping("/")//user - this is called by mentee to enroll.
    public MessageResponse addProgram(@RequestBody @Validated ProgramDTO programDTO) {
        Users mentor = userService.getUser(programDTO.getMentor_username());
        Users mentee = userService.getUser(programDTO.getMentee_username());
        Subject subject = subjectService.getByKeys(programDTO.getSubject_name(), programDTO.getSubsubject_name());
        programDTO.setProgram_id(programService.getMax() + 1);
        Program program = programMapper.mapToEntity(programDTO, mentor, mentee, subject);
        return programService.addProgram(program);
    }

    /**
     * <h1> Update A Program -- Endpoint</h1>
     * By this endpoint, users and admins can update the information of a program.
     * It takes contents of the program in the body of the request.
     * <br/>
     * This endpoint acts as an ambassador and returns the "updateProgram" function of service class.
     *
     * @param program_id ID of the program.
     * @param programDTO Contents of the updated program.
     * @return MessageResponse: SUCCESS upon successful operation.
     */
    @PreAuthorize("hasAnyAuthority('ROLE_ADMINS','ROLE_USERS')")
    @PutMapping("/{program_id}")//admin-user
    public MessageResponse updateProgram(@PathVariable Long program_id, @RequestBody @Validated ProgramDTO programDTO) {
        return programService.updateProgram(program_id, programDTO);
    }

    /**
     * <h1> Create Phases Of a Program As a User -- Endpoint</h1>
     * By this endpoint, users can initialize the phases of their programs.
     * It takes the number of phases to create as a param.
     * <br/>
     * This endpoint acts as an ambassador and returns the "addPhases" function of service class if program really exists.
     *
     * @param program_id  ID of the program.
     * @param phase_count Number of the phases to create.
     * @return MessageResponse: SUCCESS
     */
    @PreAuthorize("hasAuthority('ROLE_USERS')")
    @PutMapping("/{program_id}/phases/{phase_count}")//user
    public MessageResponse addPhases(@PathVariable String program_id, @PathVariable String phase_count) {
        Program program = programService.getById(Long.valueOf(program_id));
        if (program == null) return new MessageResponse("Böyle bir program yok.", MessageType.ERROR);
        return programService.addPhases(Long.valueOf(program_id), Integer.valueOf(phase_count));
    }

    /**
     * <h1> Update A Phase By Program ID As a User -- Endpoint</h1>
     * By this endpoint, users can update the phases of their programs.
     * It takes contents of the phase in the body of the request.
     * <br/>
     * This endpoint acts as an ambassador and returns the "updatePhase" function of service class if program really exists.
     *
     * @param program_id ID of the program.
     * @param phaseDTO   Contents of the updated phase.
     * @return MessageResponse: SUCCESS upon successful operation, ERROR with an explanation otherwise.
     */
    @PreAuthorize("hasAuthority('ROLE_USERS')")
    @PutMapping("/{program_id}/updatePhase")//user
    public MessageResponse updatePhase(@PathVariable Long program_id, @RequestBody @Validated PhaseDTO phaseDTO) {
        Program program = programService.getById(program_id);
        if (program == null) return new MessageResponse("Böyle bir program yok.", MessageType.ERROR);
        return programService.updatePhase(phaseDTO);
    }

    /**
     * <h1> Move On To Next Phase Of a Program As a User-- Endpoint</h1>
     * By this endpoint, users can close active phase and move on to next phase if exists.
     * If this was the final one, then finishes the porgram.
     * It takes contents of the phase in the body of the request.
     * <br/>
     * This endpoint acts as an ambassador and returns the "nextPhase" function of service class if program really exists.
     *
     * @param program_id ID of the program.
     * @param phaseDTO   Contents of the phase to be closed.
     * @return MessageResponse: SUCCESS upon successful operation, ERROR with an explanation otherwise.
     */
    @PreAuthorize("hasAuthority('ROLE_USERS')")
    @PutMapping("/{program_id}/nextPhase")//user
    public MessageResponse nextPhase(@PathVariable Long program_id, @RequestBody @Validated PhaseDTO phaseDTO) {
        Program program = programService.getById(program_id);
        if (program == null) return new MessageResponse("Böyle bir program yok.", MessageType.ERROR);
        return programService.nextPhase(program, phaseDTO);
    }

}

package obss.intern.veyis.service;

import lombok.RequiredArgsConstructor;
import obss.intern.veyis.config.response.MessageResponse;
import obss.intern.veyis.config.response.MessageType;
import obss.intern.veyis.manageMentorships.entity.Phase;
import obss.intern.veyis.manageMentorships.entity.Program;
import obss.intern.veyis.manageMentorships.repository.ProgramRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramService {

    private final ProgramRepository programRepository;

    public MessageResponse updatePhase(Long program_id, Phase phase) {
        //TODO
        return new MessageResponse("", MessageType.ERROR);
    }

    public MessageResponse addProgram(Program program) {
        Program programFromDB = programRepository.getById(program.getProgram_id().getProgram_id());
        if (programFromDB != null) {
            return new MessageResponse("Program zaten var!", MessageType.ERROR);
        }
        programRepository.save(program);
        return new MessageResponse("Eklendi.", MessageType.SUCCESS);
    }

    public Program getByKeys(String mentee_username, String mentor_username, String subject_name, String subsubject_name) {
        return programRepository.findByKeys(mentee_username, mentor_username, subject_name, subsubject_name);
    }

    public Program getById(Long program_id) {
        return programRepository.getById(program_id);
    }

    public List<Program> getAllPrograms() {
        return programRepository.findAll();
    }

    public MessageResponse closePhase(Long program_id, Phase phase) {

        //TODO
        return new MessageResponse("", MessageType.ERROR);
    }
}

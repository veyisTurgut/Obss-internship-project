package obss.intern.veyis.service;

import lombok.RequiredArgsConstructor;
import obss.intern.veyis.config.response.MessageResponse;
import obss.intern.veyis.config.response.MessageType;
import obss.intern.veyis.manageMentorships.entity.Phase;
import obss.intern.veyis.manageMentorships.entity.Program;
import obss.intern.veyis.manageMentorships.repository.PhaseRepository;
import obss.intern.veyis.manageMentorships.repository.ProgramRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgramService {

    private final ProgramRepository programRepository;
    private final PhaseRepository phaseRepository;

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
        return programRepository.getProgramById(program_id);
    }

    public List<Program> getAllPrograms() {
        return programRepository.findAll();
    }

    public MessageResponse closePhase(Long program_id, Phase phase) {
        Program program = programRepository.getProgramById(program_id);
        List<Phase> phases = program.getPhases().stream().collect(Collectors.toList());
        if (phases.size() == 0) {
            return new MessageResponse("Henüz hiç faz yok!", MessageType.ERROR);
        }
        Collections.sort(phases);
/*
        Set<Phase> filteredPhases = program.getPhases()
                .stream()
                .filter(x -> !x.getId().getPhase_id().equals(phase.getId()))
                .collect(Collectors.toSet());
        program.getPhases().clear();
        program.getPhases().addAll(filteredPhases);*/

        Phase phase_from_db = phaseRepository.getPhaseById(phase.getId().getPhase_id(), phase.getId().getProgram_id().getProgram_id());

        if (phase.getMentor_point() == null) {
            //mentee updated first
            phase_from_db.setMentee_point(phase.getMentee_point());
            phase_from_db.setMentee_experience(phase.getMentee_experience());
        } else {
            phase_from_db.setMentor_point(phase.getMentor_point());
            phase_from_db.setMentor_experience(phase.getMentor_experience());
        }
        phaseRepository.save(phase_from_db);

        Phase next_phase = phaseRepository.getPhaseById(phase.getId().getPhase_id() + 1, phase.getId().getProgram_id().getProgram_id());
        if (next_phase == null) {// program is finished
//TODO
            return new MessageResponse("Program bitti.", MessageType.SUCCESS);

        } else {
//TODO
            return new MessageResponse("Faz " + phase.getId().getPhase_id() + " tamamlandı.", MessageType.SUCCESS);
        }
    }
}

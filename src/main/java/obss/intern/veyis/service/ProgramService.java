package obss.intern.veyis.service;

import lombok.RequiredArgsConstructor;
import obss.intern.veyis.config.response.MessageResponse;
import obss.intern.veyis.config.response.MessageType;
import obss.intern.veyis.manageMentorships.dto.PhaseDTO;
import obss.intern.veyis.manageMentorships.dto.ProgramDTO;
import obss.intern.veyis.manageMentorships.entity.Phase;
import obss.intern.veyis.manageMentorships.entity.Program;
import obss.intern.veyis.manageMentorships.entity.Users;
import obss.intern.veyis.manageMentorships.repository.PhaseRepository;
import obss.intern.veyis.manageMentorships.repository.ProgramRepository;
import obss.intern.veyis.manageMentorships.repository.UserRepository;
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
    private final UserRepository userRepository;

    public MessageResponse updatePhase(Long program_id, Phase phase) {
        //TODO
        return new MessageResponse("", MessageType.ERROR);
    }

    public MessageResponse addProgram(Program program) {//TODO further checks
        Program programFromDB = programRepository.getProgramById(program.getProgram_id().getProgram_id());// bu check hatalı, idyi yni oluşturduk. mentor,mentee,subjecete falan bak!
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
        Phase phase_from_db = phaseRepository.getPhaseById(phase.getId().getPhase_id(), program_id);

        if (phase.getMentor_point() == null) {
            //mentee updated first
            phase_from_db.setMentee_point(phase.getMentee_point());
            phase_from_db.setMentee_experience(phase.getMentee_experience());
        } else {
            phase_from_db.setMentor_point(phase.getMentor_point());
            phase_from_db.setMentor_experience(phase.getMentor_experience());
        }
        phase_from_db.setEnd_date(new Date(System.currentTimeMillis()));
        phaseRepository.save(phase_from_db);

        Phase next_phase = phaseRepository.getPhaseById(phase.getId().getPhase_id() + 1, program_id);
        if (next_phase == null) {// program is finished
            program.setEnd_date(new Date(System.currentTimeMillis()));
            program.setStatus("Ended");
            program.setIs_active(false);
            programRepository.save(program);
            return new MessageResponse("Program bitti.", MessageType.SUCCESS);
        } else {
            next_phase.setStart_date(new Date(System.currentTimeMillis()));
            phaseRepository.save(next_phase);
            return new MessageResponse("Faz " + phase.getId().getPhase_id() + " tamamlandı.", MessageType.SUCCESS);
        }
    }

    public MessageResponse addPhases(Program program, Set<Phase> phases) {
        phaseRepository.saveAll(phases);
        program.setPhases(phases);
        programRepository.save(program);
        return new MessageResponse("Başarılı sanırım, hatayı nasıl yakalıycam bilemedim burda.", MessageType.SUCCESS);
    }

    public Long getMax() {
        return programRepository.getMaxId().getProgram_id().getProgram_id();
    }

    public MessageResponse addMentee(Long program_id, String mentee_username) {
        Program program = programRepository.getById(program_id);
        if (program == null) return new MessageResponse("Program bulunamadı!", MessageType.ERROR);
        Users mentee = userRepository.findByUsername(mentee_username);
        if (mentee == null)
            return new MessageResponse("Mentee bulunamadı! Önce onu bir kullanıcı olarak kaydet!", MessageType.ERROR);
        List<Program> activePrograms = programRepository.findAllActive();
        if (!activePrograms.contains(program)) return new MessageResponse("Program dolu!", MessageType.ERROR);
        program.setMentee(mentee);
        programRepository.save(program);
        return new MessageResponse("Mentee eklendi!", MessageType.SUCCESS);
    }

    public List<Program> getActivePrograms() {
        return programRepository.findAllActive();
    }
}

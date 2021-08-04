package obss.intern.veyis.service;

import lombok.RequiredArgsConstructor;
import obss.intern.veyis.config.response.MessageResponse;
import obss.intern.veyis.config.response.MessageType;
import obss.intern.veyis.manageMentorships.dto.ApplicationDTO;
import obss.intern.veyis.manageMentorships.dto.PhaseDTO;
import obss.intern.veyis.manageMentorships.dto.ProgramDTO;
import obss.intern.veyis.manageMentorships.entity.MentorshipApplication;
import obss.intern.veyis.manageMentorships.entity.Phase;
import obss.intern.veyis.manageMentorships.entity.Program;
import obss.intern.veyis.manageMentorships.entity.Users;
import obss.intern.veyis.manageMentorships.repository.ApplicationRepository;
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
    private final ApplicationRepository applicationRepository;

    public MessageResponse updatePhase(Phase phase) {
        //TODO: actually i dont need to map dto to entity in controller, i could directly send dto to this function
        //TODO: check edge cases: whether phase exists, who sent the request?, etc
        Phase phase_from_db = phaseRepository.getPhaseById(phase.getId().getPhase_id(), phase.getId().getProgram_id().getProgram_id());
        if (phase.getMentor_point() == null) {
            phase_from_db.setMentee_experience(phase.getMentee_experience());
            phase_from_db.setMentee_point(phase.getMentee_point());
        } else if (phase.getMentee_point() == null) {
            phase_from_db.setMentor_experience(phase.getMentor_experience());
            phase_from_db.setMentor_point(phase.getMentor_point());
        }
        phaseRepository.save(phase_from_db);
        return new MessageResponse("", MessageType.ERROR);
    }

    public MessageResponse addProgram(Program program) {//TODO further checks
        if (program.getMentee() == null || program.getMentor() == null || program.getSubject() == null) {
            return new MessageResponse("Json hatalı!", MessageType.ERROR);
        }
        Program programFromDB = programRepository.findByKeys(
                program.getMentee().getUsername(),
                program.getMentor().getUsername(),
                program.getSubject().getSubject_name(),
                program.getSubject().getSubsubject_name());
        if (programFromDB != null) {
            return new MessageResponse("Program zaten var!", MessageType.ERROR);
        }
        List<Program> programs_of_this_mentor = programRepository.findProgramByMentorEqualsAndSubjectEquals(program.getMentor(), program.getSubject());
        if (programs_of_this_mentor.size() >= 2) {
            MentorshipApplication application = applicationRepository.findApprovedByKeys(program.getMentor().getUsername(), program.getSubject().getSubject_id());
            if (application == null) return new MessageResponse("Kaydolunamaz!", MessageType.ERROR);
            application.setStatus("full");
            applicationRepository.save(application);
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
        //TODO: zaen kapalı olan fazı tekrar kapama!
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

    public MessageResponse startPhase1(Program program) {
        List<Phase> phases = program.getPhases().stream().collect(Collectors.toList());
        Collections.sort(phases);
        //Phase phase1 = (Phase) phases.stream().filter(x -> x.getId().getPhase_id().equals(1)).toArray()[0];
        phases.get(0).setStart_date(new Date(System.currentTimeMillis()));
        program.setPhases(phases.stream().collect(Collectors.toSet()));
        phaseRepository.save(phases.get(0));
        return new MessageResponse("todo", MessageType.SUCCESS);
    }
    /*
    public List<Program> getActivePrograms() {
        return programRepository.findAllActive();
    }

     */
}

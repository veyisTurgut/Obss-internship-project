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
import obss.intern.veyis.util.EmailSender;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgramService {

    private final ProgramRepository programRepository;
    private final PhaseRepository phaseRepository;
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;


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
        System.out.println(programs_of_this_mentor);
        if (programs_of_this_mentor.size() >= 1) {
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

    public MessageResponse nextPhase(Program program, PhaseDTO phaseDTO) {
        List<Phase> phases = program.getPhases().stream().collect(Collectors.toList());
        if (phases.size() == 0) {
            return new MessageResponse("Henüz hiç faz yok!", MessageType.ERROR);
        }
        Collections.sort(phases);
        if (phaseDTO.getPhase_id() == 0) {//Starting phase 1
            phases.get(0).setStart_date(new Date(System.currentTimeMillis()));
            phaseRepository.save(phases.get(0));
            program.setStatus("faz 1");
            programRepository.save(program);

            return new MessageResponse("", MessageType.SUCCESS);
        }
        if (phaseDTO.getMentor_point() == null) {
            phases.get(phaseDTO.getPhase_id().intValue() - 1).setMentee_point(phaseDTO.getMentee_point());
            phases.get(phaseDTO.getPhase_id().intValue() - 1).setMentee_experience(phaseDTO.getMentee_experience());
        } else {
            phases.get(phaseDTO.getPhase_id().intValue() - 1).setMentor_experience(phaseDTO.getMentor_experience());
            phases.get(phaseDTO.getPhase_id().intValue() - 1).setMentor_point(phaseDTO.getMentor_point());
        }
        phases.get(phaseDTO.getPhase_id().intValue() - 1).setEnd_date(new Date(System.currentTimeMillis()));
        if (phases.size() == phaseDTO.getPhase_id().intValue()) {//this was the last phase
            program.setEnd_date(new Date(System.currentTimeMillis()));
            program.setStatus("Bitti");
        } else {
            phases.get(phaseDTO.getPhase_id().intValue()).setStart_date(new Date(System.currentTimeMillis()));
            program.setStatus("faz " + Integer.valueOf(phaseDTO.getPhase_id().intValue() + 1));
        }
        programRepository.save(program);
        phaseRepository.saveAll(phases);

        return new MessageResponse("Başarılı", MessageType.SUCCESS);
    }

    public MessageResponse updatePhase(PhaseDTO phasedto) {
        Phase phase_from_db = phaseRepository.getPhaseById(phasedto.getPhase_id(), phasedto.getProgram_id());
        if (phase_from_db == null) return new MessageResponse("Böyle bir faz yok.", MessageType.ERROR);
        if (phasedto.getMentor_point() == null) {
            phase_from_db.setMentee_experience(phasedto.getMentee_experience());
            phase_from_db.setMentee_point(phasedto.getMentee_point());
        } else if (phasedto.getMentee_point() == null) {
            phase_from_db.setMentor_experience(phasedto.getMentor_experience());
            phase_from_db.setMentor_point(phasedto.getMentor_point());
        }
        if (phasedto.getExpected_end_date() != null) {
            String cron_date = "0 " + phasedto.getExpected_end_date().getMinutes() + " " + (phasedto.getExpected_end_date().getHours() - 4) + " " +
                    phasedto.getExpected_end_date().getDate() + " " + (phasedto.getExpected_end_date().getMonth() + 1) + " *";
            phase_from_db.setExpected_end_date(phasedto.getExpected_end_date());
            ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
            taskScheduler.initialize();
            taskScheduler.schedule(new EmailSender(phase_from_db), new CronTrigger(cron_date));
            taskScheduler.getScheduledThreadPoolExecutor().shutdown();
        }
        phaseRepository.save(phase_from_db);
        return new MessageResponse("Başarıyla güncellendi.", MessageType.SUCCESS);
    }

    public MessageResponse addPhases(Long program_id, Integer phaseCount) {
        for (int i = 1; i < phaseCount; i++) {
            phaseRepository.addByIds(program_id, i);
        }
        return new MessageResponse("Başarılı.", MessageType.SUCCESS);
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

    public MessageResponse updateProgram(Long program_id, ProgramDTO programDTO) {
        Program program = programRepository.getProgramById(program_id);
        if (programDTO.getMentee_comment() != null)
            program.setMentee_comment(programDTO.getMentee_comment());
        if (programDTO.getMentor_comment() != null)
            program.setMentor_comment(programDTO.getMentor_comment());
        programRepository.save(program);
        return new MessageResponse("Yorum eklendi!", MessageType.SUCCESS);
    }
    /*
    public List<Program> getActivePrograms() {
        return programRepository.findAllActive();
    }

     */
}

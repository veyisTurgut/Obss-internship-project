package obss.intern.veyis.service;

import lombok.RequiredArgsConstructor;
import obss.intern.veyis.config.response.MessageResponse;
import obss.intern.veyis.config.response.MessageType;
import obss.intern.veyis.manageMentorships.dto.PhaseDTO;
import obss.intern.veyis.manageMentorships.dto.ProgramDTO;
import obss.intern.veyis.manageMentorships.entity.MentorshipApplication;
import obss.intern.veyis.manageMentorships.entity.Phase;
import obss.intern.veyis.manageMentorships.entity.Program;
import obss.intern.veyis.manageMentorships.repository.ApplicationRepository;
import obss.intern.veyis.manageMentorships.repository.PhaseRepository;
import obss.intern.veyis.manageMentorships.repository.ProgramRepository;
import obss.intern.veyis.util.EmailSender;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgramService {

    private static final Integer MAX_MENTEE_COUNT_PER_SUBJECT = 2;
    private final ProgramRepository programRepository;
    private final PhaseRepository phaseRepository;
    private final ApplicationRepository applicationRepository;

    /**
     * <h1> Get All Programs -- Service</h1>
     * This functions fetches all the programs and returns it.
     *
     * @return List<Program>: List of Program's.
     * @see Program
     */
    public List<Program> getAllPrograms() {
        return programRepository.findAll();
    }

    /**
     * <h1> Get A Program By ID -- Service</h1>
     * This functions fetches the program with the given id if exists and returns it.
     *
     * @param program_id Id of the program.
     * @return Program
     */
    public Program getById(Long program_id) {
        return programRepository.getProgramById(program_id);
    }

    /**
     * <h1> Add a Program As a Mentee -- Service</h1>
     * We first check whether any of the mentor, mentee or subject is nonvalid.
     * Secondly check whether mentor and mentee are the same person.
     * Then check whether program already exists.
     * Finally we check whether this mentor already working with 2 mentees.
     *
     * @param program Contents of the program.
     * @return MessageResponse: SUCCESS upon successful operation, ERROR with an explanation otherwise.
     */
    public MessageResponse addProgram(Program program) {
        //check whether any of the mentor, mentee or subject is nonvalid.
        if (program.getMentee() == null || program.getMentor() == null || program.getSubject() == null)
            return new MessageResponse("Json hatalı!", MessageType.ERROR);
        //check whether mentor and mentee are the same person
        if (program.getMentor().equals(program.getMentee()))
            return new MessageResponse("Mentor ile mentee aynı kişi olamaz!", MessageType.ERROR);

        //check whether program already exists.
        Program programFromDB = programRepository.findByKeys(
                program.getMentee().getUsername(),
                program.getMentor().getUsername(),
                program.getSubject().getSubject_name(),
                program.getSubject().getSubsubject_name());
        if (programFromDB != null) {
            return new MessageResponse("Program zaten var!", MessageType.ERROR);
        }
        //check whether this user working with another mentor on this subject
        List<Program> programs_of_this_mentee = programRepository.findProgramByMentee(program.getMentee().getUsername());
        //after
        if (!programs_of_this_mentee.stream().filter(x -> x.getSubject().getSubject_name().equals(program.getSubject().getSubject_name()))
                .map(x -> x.getMentor().getUsername()).collect(Collectors.toSet()).equals(Collections.emptySet())
                && !programs_of_this_mentee.stream().filter(x -> x.getSubject().getSubject_name().equals(program.getSubject().getSubject_name()))
                .map(x -> x.getMentor().getUsername()).collect(Collectors.toSet()).contains(program.getMentor().getUsername())) {
            return new MessageResponse("Aynı ana konu için sadece 1 mentor ile çalışabilirsin.", MessageType.ERROR);
        }
        /* // before
        if (!programs_of_this_mentee.stream().filter(x -> x.getSubject().getSubject_id() == program.getSubject().getSubject_id())
                .map(x->x.getMentor().getUsername()).collect(Collectors.toSet()).contains(program.getMentor().getUsername())){
            return new MessageResponse("Aynı ana konu için sadece 1 mentor ile çalışabilirsin.",MessageType.ERROR);
        }*/

        //check whether this mentor already working with 2 mentees, if so make application full.
        List<Program> programs_of_this_mentor = programRepository.findProgramByMentorEqualsAndSubjectEquals(program.getMentor(), program.getSubject());
        if (programs_of_this_mentor.size() >= MAX_MENTEE_COUNT_PER_SUBJECT - 1) {
            MentorshipApplication application = applicationRepository.findApprovedByKeys(program.getMentor().getUsername(), program.getSubject().getSubject_id());
            if (application == null)
                return new MessageResponse("Mentor zaten 2 kişi ile çalışıyor!", MessageType.ERROR);
            application.setStatus("full");
            applicationRepository.save(application);
        }
        programRepository.save(program);
        return new MessageResponse("Eklendi.", MessageType.SUCCESS);
    }

    /**
     * <h1> Update A Program -- Service</h1>
     * This function updates the comments of a mentor or a mentee, and saves it to database.
     *
     * @param program_id ID of the program.
     * @param programDTO Contents of the updated program.
     * @return MessageResponse: SUCCESS.
     */
    public MessageResponse updateProgram(Long program_id, ProgramDTO programDTO) {
        Program program = programRepository.getProgramById(program_id);
        if (programDTO.getMentee_comment() != null)
            program.setMentee_comment(programDTO.getMentee_comment());
        if (programDTO.getMentor_comment() != null)
            program.setMentor_comment(programDTO.getMentor_comment());
        programRepository.save(program);
        return new MessageResponse("Yorum eklendi!", MessageType.SUCCESS);
    }

    /**
     * <h1> Create Phases Of a Program -- Service</h1>
     * This function creates as many phases as parameter phase_count of program given.
     * Most of the fields of these phases are null, to be filled later.
     *
     * @param program_id  ID of the program.
     * @param phase_count Number of the phases to create.
     * @return MessageResponse: SUCCESS
     */
    public MessageResponse addPhases(Long program_id, Integer phase_count) {
        for (int i = 1; i <= phase_count; i++) {
            phaseRepository.addByIds(program_id, i);
        }
        return new MessageResponse("Başarılı.", MessageType.SUCCESS);
    }

    /**
     * <h1> Update A Phase -- Service</h1>
     * This function takes updated phase as parameter and changes the necessary fields in database.
     * <br/>
     * First it checks whether phase exists.
     * Then, checks whether mentee or mentor updated and acts upon that.
     * <br/>
     * Also, it updates expected end date of a phase if given in the phaseDTO and
     * schedules an e-mail to both mentee and mentor 1 hour before that expected date.
     * Finally saves the updated phase to database.
     *
     * @param phaseDTO Contents of the updated phase.
     * @return MessageResponse: SUCCESS upon successful operation, ERROR with an explanation otherwise.
     */
    public MessageResponse updatePhase(PhaseDTO phaseDTO) {
        Phase phase_from_db = phaseRepository.getPhaseById(phaseDTO.getPhase_id(), phaseDTO.getProgram_id());
        if (phase_from_db == null)
            return new MessageResponse("Böyle bir faz yok.", MessageType.ERROR);
        if (phaseDTO.getMentee_point() != null) {
            phase_from_db.setMentee_experience(phaseDTO.getMentee_experience());
            phase_from_db.setMentee_point(phaseDTO.getMentee_point());
        } else if (phaseDTO.getMentor_point() != null) {
            phase_from_db.setMentor_experience(phaseDTO.getMentor_experience());
            phase_from_db.setMentor_point(phaseDTO.getMentor_point());
        }
        if (phaseDTO.getExpected_end_date() != null) {
            //expected end date of current phase cant be earlier than the previous one.
            if (phaseDTO.getPhase_id() > 1) {
                Date prev_phase_expected_end = phaseRepository.getPhaseById(phaseDTO.getPhase_id() - 1, phaseDTO.getProgram_id()).getExpected_end_date();
                if (prev_phase_expected_end != null && prev_phase_expected_end.compareTo(phaseDTO.getExpected_end_date()) >= 0) {
                    return new MessageResponse("Bu fazın bitişi önceki fazın tahmini bitiş tarihinden sonra olmalı.", MessageType.ERROR);
                }
            }
            //expected end date of current phase cant be later than the next one.
            //TODO check whether this is last phase!
            Date next_phase_expected_end = phaseRepository.getPhaseById(phaseDTO.getPhase_id() + 1, phaseDTO.getProgram_id()).getExpected_end_date();
            if (next_phase_expected_end != null && next_phase_expected_end.compareTo(phaseDTO.getExpected_end_date()) <= 0) {
                return new MessageResponse("Bu fazın bitişi sonraki fazın tahmini bitiş tarihinden önce olmalı.", MessageType.ERROR);
            }

            /* EXAMPLE:
                given date: Mon Aug 16 16:58:44 TRT 2021
                created cron: 0 58 12 16 8 *
                * First 0 is second.
                * 58 is minute
                * 12 is 16-3-1. -3 is because timezone, -1 is for 1 hour before
                * 8 is for august, i added1 because, getMonth() returns 7 for august.
                * Final * stands for current year.

                Final note: If expected final date due is longer than a year, this may fail.
             */
            String cron_date = "0 " + phaseDTO.getExpected_end_date().getMinutes() + " " + (phaseDTO.getExpected_end_date().getHours() - 4) + " " +
                    phaseDTO.getExpected_end_date().getDate() + " " + (phaseDTO.getExpected_end_date().getMonth() + 1) + " *";
            phase_from_db.setExpected_end_date(phaseDTO.getExpected_end_date());
            ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
            taskScheduler.initialize();
            taskScheduler.schedule(new EmailSender(phase_from_db), new CronTrigger(cron_date));
            taskScheduler.getScheduledThreadPoolExecutor().shutdown();
        }
        phaseRepository.save(phase_from_db);
        return new MessageResponse("Başarıyla güncellendi.", MessageType.SUCCESS);
    }

    /**
     * <h1> Move On To Next Phase Of a Program As a User-- Endpoint</h1>
     * This function takes the program and phase to be closed as a parameter, it closes the phase and opens next if exist.
     * <br/>
     * First it checks whether program has any phases.
     * Then checks whether this is the first phase. Phase_id being equal 0 means "start phase 1".
     * If previous check did not hold, we have some feedback in phaseDTO parameter by mentee or mentor.
     * Since it's one of them who closed the phase and we asked for their feedback at that time.
     * We update the given phase with respect to their feedbacks and look for next phase.
     * Finally if this was the last phase, we close the program; otherwise update the status of the program,
     * start next phase and save all these changes to database.
     *
     * @param program  Program object.
     * @param phaseDTO Contents of the phase to be closed.
     * @return MessageResponse: SUCCESS upon successful operation, ERROR with an explanation otherwise.
     */
    public MessageResponse nextPhase(Program program, PhaseDTO phaseDTO) {
        List<Phase> phases = program.getPhases().stream().collect(Collectors.toList());
        if (phases.size() == 0) {
            return new MessageResponse("Henüz hiç faz yok!", MessageType.ERROR);
        }
        Collections.sort(phases);
        if (phaseDTO.getPhase_id() == 0) {//Starting phase 1
            phases.get(0).setStart_date(new Date(System.currentTimeMillis()));
            phaseRepository.save(phases.get(0));
            program.setStatus("Faz 1");
            programRepository.save(program);
            return new MessageResponse("Süreç başarıyla başlatıldı.", MessageType.SUCCESS);
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
            program.setStatus("Faz " + Integer.valueOf(phaseDTO.getPhase_id().intValue() + 1));
        }
        programRepository.save(program);
        phaseRepository.saveAll(phases);

        return new MessageResponse("Başarıyla kaydedildi.", MessageType.SUCCESS);
    }

    /**
     * This is a helper function to fetch the id of the last program.
     */
    public Long getMax() {
        Program max_id = programRepository.getMaxId();
        return max_id == null ? 0 : max_id.getProgram_id().getProgram_id();
    }


}

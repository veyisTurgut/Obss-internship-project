package obss.intern.veyis.service;

import lombok.RequiredArgsConstructor;
import obss.intern.veyis.config.response.MessageResponse;
import obss.intern.veyis.config.response.MessageType;
import obss.intern.veyis.manageMentorships.dto.ApplicationDTO;
import obss.intern.veyis.manageMentorships.dto.SubjectDTO;
import obss.intern.veyis.manageMentorships.entity.MentorshipApplication;
import obss.intern.veyis.manageMentorships.entity.Program;
import obss.intern.veyis.manageMentorships.entity.Subject;
import obss.intern.veyis.manageMentorships.entity.Users;
import obss.intern.veyis.manageMentorships.entity.compositeKeys.ProgramId;
import obss.intern.veyis.manageMentorships.repository.*;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final ProgramRepository programRepository;
    private final SubjectRepository subjectRepository;

    public List<MentorshipApplication> findAllApplications() {
        List<MentorshipApplication> list = applicationRepository.findAllApplications();
        return list;
    }


    public MessageResponse addMentorshipApplication(MentorshipApplication application) {
        //first check whether subject and user exists
        if (application.getApplicant() == null) {
            return new MessageResponse("user not found", MessageType.ERROR);
        }
        if (application.getSubject() == null) {
            return new MessageResponse("subject not found", MessageType.ERROR);
        }

        MentorshipApplication application_from_db = applicationRepository.findAllByKeys(application.getApplicant().getUsername(), application.getSubject().getSubject_id());
        if (application_from_db != null) {
            return new MessageResponse("application already exists", MessageType.ERROR);
        }
        applicationRepository.saveManually(application.getApplicant().getUsername(), application.getSubject().getSubject_id(), application.getExperience());
        return new MessageResponse("success", MessageType.SUCCESS);
    }


    private MentorshipApplication findFromDto(ApplicationDTO applicationDTO) {
        Subject subject = subjectRepository.findSubject(applicationDTO.getSubject_name(), applicationDTO.getSubsubject_name());
        if (subject == null) return null;
        MentorshipApplication application = applicationRepository.findAllByKeys(applicationDTO.getApplicant_username(), subject.getSubject_id());
        if (application == null) return null;
        return application;
    }

    public MessageResponse rejectMentorshipApplication(ApplicationDTO applicationDTO) {
        MentorshipApplication application_from_db = findFromDto(applicationDTO);
        if (application_from_db == null) return new MessageResponse("Başvuru bulunamadı!", MessageType.ERROR);
        application_from_db.setStatus("rejected");
        applicationRepository.save(application_from_db);
        return new MessageResponse("rejected.", MessageType.SUCCESS);
    }


    public MessageResponse updateMentorshipApplication(ApplicationDTO applicationDTO) {
        MentorshipApplication application_from_db = findFromDto(applicationDTO);
        if (application_from_db == null) return new MessageResponse("Başvuru bulunamadı!", MessageType.ERROR);
        application_from_db.setExperience(applicationDTO.getExperience());
        applicationRepository.save(application_from_db);
        return new MessageResponse("experience updated.", MessageType.SUCCESS);
    }

    public MessageResponse approveMentorshipApplication(ApplicationDTO applicationDTO) {
        MentorshipApplication application_from_db = findFromDto(applicationDTO);
        if (application_from_db == null)
            return new MessageResponse("Başvuru bulunamadı!", MessageType.ERROR);
        if (!application_from_db.getStatus().equals("open"))
            return new MessageResponse("Aktif başvuru bulunamadı!", MessageType.ERROR);

        application_from_db.setStatus("approved");
        applicationRepository.save(application_from_db);
        /*
        Program new_program = new Program();
        ProgramId id = new ProgramId();
        id.setProgram_id(programRepository.getMaxId().getProgram_id().getProgram_id() + 1);
        new_program.setProgram_id(id);
        new_program.setStatus("did not began");
        new_program.setIs_active(true);
        new_program.setSubject(application_from_db.getSubject());
        // new_program.setProgram_id(programRepository.getMaxId().getProgram_id());
        new_program.setMentor(application_from_db.getApplicant());
        programRepository.save(new_program);

         */
        return new MessageResponse("Başvuru onaylandı.", MessageType.SUCCESS);

    }


    public List<MentorshipApplication> findApprovedApplications() {
        return applicationRepository.findMentorshipApplicationsByStatusEquals("approved");
    }

    public List<MentorshipApplication> findOpenApplications() {
        return applicationRepository.findMentorshipApplicationsByStatusEquals("open");
    }

    public List<MentorshipApplication> findByKeyword(String keyword, List<SubjectDTO> subjectDTOs) {
        Set<MentorshipApplication> set = new HashSet<>();
        for (SubjectDTO subjectDTO : subjectDTOs) {
            Subject subject_from_db = subjectRepository.findSubject(subjectDTO.getSubject_name(), subjectDTO.getSubsubject_name());
            if (subject_from_db == null) continue;
            set.addAll(applicationRepository.findByKeywordAndSubject(keyword, subject_from_db.getSubject_id()));
        }
        return set.stream().collect(Collectors.toList());
    }

    public MessageResponse deleteMentorshipApplication(Subject subject, Users mentor) {
        MentorshipApplication application = applicationRepository.findAllByKeys(mentor.getUsername(), subject.getSubject_id());
        applicationRepository.delete(application);
        return new MessageResponse("Silindi!", MessageType.SUCCESS);
    }

    public List<MentorshipApplication> findSubjectsUserCanApply(String mentee) {
        List<Program> users = programRepository.findProgramByMentee(mentee);
        Set<MentorshipApplication> approved = findApprovedApplications().stream().collect(Collectors.toSet());
        if (users.size() == 0) {
           approved.stream().collect(Collectors.toList());
        }
        List<Subject> subjects = users.stream().filter(x -> !x.getStatus().equals("ended")).
                map(x -> x.getSubject()).collect(Collectors.toList());

/*
        System.out.println();
        System.out.println();
        for (Subject subject : subjects) {
            System.out.println(subject);
        }
        System.out.println();
        System.out.println();
        for (MentorshipApplication a : approved) {
            System.out.println(a.getSubject());
        }
        System.out.println();
        System.out.println();*/
        approved = approved.stream().filter(x -> !subjects.contains(x.getSubject())).collect(Collectors.toSet());
        /*for (MentorshipApplication a : approved) {
            System.out.println(a.getSubject());
        }*/
        return approved.stream().collect(Collectors.toList());
    }

}
package obss.intern.veyis.service;

import lombok.RequiredArgsConstructor;
import obss.intern.veyis.config.response.MessageResponse;
import obss.intern.veyis.config.response.MessageType;
import obss.intern.veyis.manageMentorships.dto.ApplicationDTO;
import obss.intern.veyis.manageMentorships.dto.ProgramDTO;
import obss.intern.veyis.manageMentorships.entity.MentorshipApplication;
import obss.intern.veyis.manageMentorships.entity.Program;
import obss.intern.veyis.manageMentorships.entity.Subject;
import obss.intern.veyis.manageMentorships.entity.Users;
import obss.intern.veyis.manageMentorships.entity.compositeKeys.ProgramId;
import obss.intern.veyis.manageMentorships.repository.ApplicationRepository;
import obss.intern.veyis.manageMentorships.repository.ProgramRepository;
import obss.intern.veyis.manageMentorships.repository.SubjectRepository;
import obss.intern.veyis.manageMentorships.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Users user = userRepository.findByUsername(application.getApplicant().getUsername());
        if (user == null) {
            return new MessageResponse("user not found", MessageType.ERROR);
        }
        Subject subject = subjectRepository.findSubject(application.getSubject().getSubject_name(), application.getSubject().getSubsubject_name());
        if (subject == null) {
            return new MessageResponse("subject not found", MessageType.ERROR);
        }

        MentorshipApplication application_from_db = applicationRepository.findByKeys(user.getUsername(), subject.getSubject_id());
        if (application_from_db != null) {
            return new MessageResponse("application already exists", MessageType.ERROR);
        }
        applicationRepository.save(application);
        return new MessageResponse("success", MessageType.SUCCESS);
    }


    private MentorshipApplication findFromDto(ApplicationDTO applicationDTO) {
        Subject subject = subjectRepository.findSubject(applicationDTO.getSubject_name(), applicationDTO.getSubsubject_name());
        if (subject == null) return null;
        MentorshipApplication application = applicationRepository.findByKeys(applicationDTO.getApplicant_username(), subject.getSubject_id());
        if (application == null) return null;
        return application;
    }

    public MessageResponse rejectMentorshipApplication(ApplicationDTO applicationDTO) {
        MentorshipApplication application_from_db = findFromDto(applicationDTO);
        if (application_from_db == null) return new MessageResponse("Başvuru bulunamadı!", MessageType.ERROR);
        application_from_db.setStatus("rejected");
        applicationRepository.save(application_from_db);
        //applicationRepository.delete(application);
        //applicationRepository.deleteApplication(application.getApplicant().getUsername(), application.getSubject().getId());
        //applicationRepository.rejectApplication(application_from_db.getApplicant().getUsername(), application_from_db.getSubject().getSubject_id());
        return new MessageResponse("Silindi.", MessageType.SUCCESS);
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
        if (application_from_db == null || !application_from_db.getStatus().equals("open"))
            return new MessageResponse("Başvuru bulunamadı!", MessageType.ERROR);
        Users mentor_applicant = application_from_db.getApplicant();
        for (Program program : mentor_applicant.getProgramsMentored()) {
            if (program.getSubject().equals(application_from_db.getSubject())) {
                return new MessageResponse("Zaten bu konuda mentorsun!", MessageType.ERROR);
            }
        }
        //TODO: further checks, didn't come into my mind right now
        application_from_db.setStatus("approved");
        applicationRepository.save(application_from_db);
        Program new_program = new Program();
        ProgramId id = new ProgramId();
        id.setProgram_id(programRepository.getMaxId().getProgram_id().getProgram_id() + 1);
        new_program.setProgram_id(id);
        new_program.setStatus("did not began");
        new_program.setIs_active(true);
        new_program.setSubject(application_from_db.getSubject());
        // new_program.setProgram_id(programRepository.getMaxId().getProgram_id());
        new_program.setMentor(mentor_applicant);
        programRepository.save(new_program);

        return new MessageResponse("başarılı", MessageType.SUCCESS);

    }
}

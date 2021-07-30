package obss.intern.veyis.service;

import lombok.RequiredArgsConstructor;
import obss.intern.veyis.config.response.MessageResponse;
import obss.intern.veyis.config.response.MessageType;
import obss.intern.veyis.manageMentorships.dto.ApplicationDTO;
import obss.intern.veyis.manageMentorships.entity.MentorshipApplication;
import obss.intern.veyis.manageMentorships.entity.Subject;
import obss.intern.veyis.manageMentorships.entity.Users;
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
        List<MentorshipApplication> list =  applicationRepository.findAllApplications();
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

        MentorshipApplication application_from_db = applicationRepository.findByKeys(user.getUsername(),subject.getId());
        if (application_from_db != null) {
            return new MessageResponse("application already exists", MessageType.ERROR);
        }
        applicationRepository.save(application);
        return new MessageResponse("success", MessageType.SUCCESS);
    }

    public MessageResponse deleteMentorshipApplication(ApplicationDTO applicationDTO){
        Subject subject = subjectRepository.findSubject(applicationDTO.getSubject_name(),applicationDTO.getSubsubject_name());
        //subject must be nonnull, so need to check.

        MentorshipApplication application = applicationRepository.findByKeys(applicationDTO.getApplicant_username(),subject.getId());
        if (application==null){
            return new MessageResponse("Yok zaten.",MessageType.ERROR);
        }
        applicationRepository.delete(application);
        return new MessageResponse("Silindi.",MessageType.SUCCESS);


    }
}

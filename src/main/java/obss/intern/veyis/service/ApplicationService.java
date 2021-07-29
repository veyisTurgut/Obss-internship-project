package obss.intern.veyis.service;

import lombok.RequiredArgsConstructor;
import obss.intern.veyis.config.response.MessageResponse;
import obss.intern.veyis.config.response.MessageType;
import obss.intern.veyis.manageMentorships.entity.MentorshipApplication;
import obss.intern.veyis.manageMentorships.repository.ApplicationRepository;
import obss.intern.veyis.manageMentorships.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    public List<MentorshipApplication> findAllApplications(){ return applicationRepository.findAllApplications();}
    /*
    public MessageResponse addMentorshipApplication(MentorshipApplication application) {
    if (applicationRepository.findById(application.getApplication_id()).isPresent()){
        return new MessageResponse("error", MessageType.ERROR);
    }
        applicationRepository.save(application);
        return new MessageResponse("success", MessageType.SUCCESS);
    }*/
}

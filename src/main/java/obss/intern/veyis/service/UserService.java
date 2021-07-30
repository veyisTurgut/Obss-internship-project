package obss.intern.veyis.service;

import obss.intern.veyis.manageMentorships.dto.ProgramDTO;
import obss.intern.veyis.manageMentorships.entity.MentorshipApplication;
import obss.intern.veyis.manageMentorships.entity.Program;
import obss.intern.veyis.manageMentorships.entity.Users;
import obss.intern.veyis.manageMentorships.repository.ApplicationRepository;
import obss.intern.veyis.manageMentorships.repository.ProgramRepository;
import obss.intern.veyis.manageMentorships.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.control.MappingControl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProgramRepository programRepository;
    private final ApplicationRepository applicationRepository;

    public Users getUser(String username) {
        return userRepository.findByUsername(username);
    }

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public Program getProgramMentored(String username) {
        return programRepository.findProgramMentored(username);
    }

    public List<Program> getProgramsMenteed(String username) {
        return programRepository.findAllProgramsMenteed(username);
    }

    public List<MentorshipApplication> getMentorshipApplications(String username){
        Users user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }
        return applicationRepository.findMentorshipApplicationsByApplicant(user);
    }

}

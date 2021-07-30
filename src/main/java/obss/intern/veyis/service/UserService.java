package obss.intern.veyis.service;

import obss.intern.veyis.manageMentorships.dto.ProgramDTO;
import obss.intern.veyis.manageMentorships.entity.Program;
import obss.intern.veyis.manageMentorships.entity.Users;
import obss.intern.veyis.manageMentorships.repository.ProgramRepository;
import obss.intern.veyis.manageMentorships.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProgramRepository programRepository;

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

    /*public Users getUserById(Integer user_id) {
        return userRepository.findById(user_id);
    }*/
}

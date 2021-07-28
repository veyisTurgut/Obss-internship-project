package obss.intern.veyis.service;

import obss.intern.veyis.manageMentorships.entity.Program;
import obss.intern.veyis.manageMentorships.entity.Users;
import obss.intern.veyis.manageMentorships.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public Program getProgramMentored(String username) {
        return userRepository.findProgramMentored(username);
    }

    public List<Program> getProgramsMenteed(int user_id) {
        return userRepository.findAllProgramsMenteed(user_id);
    }

    public List<Program> getProgramsMenteed(String username) {
        return userRepository.findAllProgramsMenteed(username);
    }

    public Users getUserById(Integer user_id) {
        return userRepository.findById(user_id);
    }
}

package obss.intern.veyis.service;

import obss.intern.veyis.manageMentorships.entity.MentorshipApplication;
import obss.intern.veyis.manageMentorships.entity.Program;
import obss.intern.veyis.manageMentorships.entity.Users;
import obss.intern.veyis.manageMentorships.repository.ApplicationRepository;
import obss.intern.veyis.manageMentorships.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;

    public Users getUser(String username) {
        return userRepository.findByUsername(username);
    }


    /**
     * <h1> Get Programs Menteed By A User -- Service</h1>
     * This endpoint fetches all the programs that menteed by given user.
     *
     * @param username: username of the Mentee
     * @return List<Program>: List of programs.
     * @see Program
     */
    public List<Program> getProgramsMenteed(String username) {
        return userRepository.findByUsername(username).getProgramsMenteed().stream().collect(Collectors.toList());
    }

    /**
     * <h1> Get Programs Mentored By A User -- Service</h1>
     * This endpoint fetches all the programs that mentored by given user.
     *
     * @param username: username of the Mentor
     * @return List<Program>: List of programs.
     * @see Program
     */
    public List<Program> getProgramsMentored(String username) {
        return userRepository.findByUsername(username).getProgramsMentored().stream().collect(Collectors.toList());
    }

    /**
     * <h1> Get Mentorship Applications Of A User -- Service</h1>
     * This function fetches all the mentorship applications of given user.
     *
     * @param username: username of the Mentor
     * @return List<MentorshipApplication>: List of Mentorship Applications.
     * @see MentorshipApplication
     */
    public List<MentorshipApplication> getMentorshipApplications(String username) {
        Users user = userRepository.findByUsername(username);
        if (user == null) return Collections.emptyList();
        return applicationRepository.findMentorshipApplicationsByApplicant(user);
    }

    /**
     * <h1> Add a User To the Database. </h1>
     * This function is called when an unknown user logged in with Google. Saves the given user to the database.
     *
     * @param user
     * @return void
     */
    public void addUser(Users user) {
        userRepository.save(user);
    }

    /**
     * <h1> Get a User By Username And Mail . </h1>
     * This function is called when someone logged in with Google.
     * It checks whether newly logged in user has a record in the system.
     *
     * @param username
     * @param gmail
     * @return User object or null.
     */
    public Users findByNameAndMail(String username, String gmail) {
        return userRepository.findUsersByUsernameEqualsAndGmailEquals(username, gmail);
    }
}

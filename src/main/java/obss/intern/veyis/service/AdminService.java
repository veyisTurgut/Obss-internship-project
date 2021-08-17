package obss.intern.veyis.service;

import lombok.RequiredArgsConstructor;
import obss.intern.veyis.manageMentorships.entity.Admin;
import obss.intern.veyis.manageMentorships.repository.AdminRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;


    /**
     * <h1> Find Admin Using username</h1>
     *
     * @param username
     * @return Admin object
     */
    public Admin getAdmin(String username) {
        return adminRepository.findByUsername(username);
    }

    /**
     * <h1> Find Admin Using Credentials</h1>
     *
     * @param username
     * @param email
     * @return Admin object
     */
    public Admin findByNameAndMail(String username, String email) {
        return adminRepository.findAdminByUsernameEqualsAndGmailEquals(username, email);
    }
}

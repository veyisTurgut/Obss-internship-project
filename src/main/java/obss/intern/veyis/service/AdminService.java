package obss.intern.veyis.service;

import lombok.RequiredArgsConstructor;
import obss.intern.veyis.manageMentorships.entity.Admin;
import obss.intern.veyis.manageMentorships.repository.AdminRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    public Admin findByNameAndMail(String username, String email){
        return adminRepository.findAdminByUsernameEqualsAndGmailEquals(username,email);
    }
}

package obss.intern.veyis.manageMentorships.repository;

import obss.intern.veyis.manageMentorships.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This class interacts with database and fetches intended records.
 */
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Admin findAdminByUsernameEqualsAndGmailEquals(String username, String gmail);

    Admin findByUsername(String username);
}
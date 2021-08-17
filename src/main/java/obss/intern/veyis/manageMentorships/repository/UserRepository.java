package obss.intern.veyis.manageMentorships.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import obss.intern.veyis.manageMentorships.entity.Users;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @see ApplicationRepository
 */
public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByUsername(String username);

    Users findUsersByUsernameEqualsAndGmailEquals(String username, String gmail);
}
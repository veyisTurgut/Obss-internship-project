package obss.intern.veyis.manageMentorships.repository;

import obss.intern.veyis.manageMentorships.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByUsername(String username);

//    @Query(value = "SELECT * FROM admin WHERE username = ?1 AND gmail = ?2", nativeQuery = true)
    Admin findAdminByUsernameEqualsAndGmailEquals(String username, String gmail);
}
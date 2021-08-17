package obss.intern.veyis.manageMentorships.repository;

import obss.intern.veyis.manageMentorships.entity.MentorshipApplication;
import obss.intern.veyis.manageMentorships.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;


/**
 * This class interacts with database and fetches intended records.
 * It supports most of the basic queries without writing sql. Yet, there were cases that I was needed to get my hands
 * dirty with writing native queries.
 * <p/> One question might linger one's mind with native queries with parameters: SQL injection.
 * No worries, it is said to be well covered against that attack.
 */
public interface ApplicationRepository extends JpaRepository<MentorshipApplication, Long> {

    @Query(value = "SELECT * FROM mentorship_application", nativeQuery = true)
    List<MentorshipApplication> findAllApplications();

    List<MentorshipApplication> findMentorshipApplicationsByStatusEquals(String status);

    @Query(value = "SELECT * FROM mentorship_application WHERE applicant_username = ?1 AND subject_id = ?2", nativeQuery = true)
    MentorshipApplication findAllByKeys(@Param("applicant_username") String applicant_username, @Param("subject_id") Long subject_id);

    @Query(value = "SELECT * FROM mentorship_application WHERE applicant_username = ?1 AND subject_id = ?2 AND status = 'approved'", nativeQuery = true)
    MentorshipApplication findApprovedByKeys(@Param("applicant_username") String applicant_username, @Param("subject_id") Long subject_id);

    List<MentorshipApplication> findMentorshipApplicationsByApplicant(Users user);

    @Query(value = "SELECT * FROM mentorship_application WHERE applicant_username = ?1 ", nativeQuery = true)
    List<MentorshipApplication> findByUsername(String username);

    @Query(value = "INSERT INTO mentorship_application(applicant_username, subject_id, experience, status) VALUES (?1,?2,?3,'open')", nativeQuery = true)
    @Transactional
    @Modifying
    void saveManually(String username, Long subject_id, String experience);
}
















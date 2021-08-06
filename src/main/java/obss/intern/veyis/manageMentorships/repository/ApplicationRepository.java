package obss.intern.veyis.manageMentorships.repository;

import obss.intern.veyis.manageMentorships.entity.MentorshipApplication;
import obss.intern.veyis.manageMentorships.entity.Subject;
import obss.intern.veyis.manageMentorships.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface ApplicationRepository extends JpaRepository<MentorshipApplication, Long> {

    @Query(value = "SELECT * FROM mentorship_application", nativeQuery = true)
    List<MentorshipApplication> findAllApplications();

    List<MentorshipApplication> findMentorshipApplicationsByStatusEquals(String status);

    //TODO: status like openı silebilirim belki, nerede kullandığıma bağlı.
    @Query(value = "SELECT * FROM mentorship_application WHERE applicant_username = ?1 AND subject_id = ?2", nativeQuery = true)
    MentorshipApplication findAllByKeys(@Param("applicant_username") String applicant_username, @Param("subject_id") Long subject_id);

    @Query(value = "SELECT * FROM mentorship_application WHERE applicant_username = ?1 AND subject_id = ?2 AND status = 'approved'", nativeQuery = true)
    MentorshipApplication findApprovedByKeys(@Param("applicant_username") String applicant_username, @Param("subject_id") Long subject_id);

    /*
        @Query(value = "SELECT M.*, SUBJECT.subject_name, SUBJECT.subsubject_name FROM " +
                "(SELECT subject_id,experience,is_active FROM mentorship_application WHERE applicant_username = ?1) " +
                "M, SUBJECT WHERE M.subject_id = SUBJECT.subject_id",nativeQuery = true)
        */
    //@Query(value = "SELECT * FROM mentorship_application WHERE applicant_username = ?1",nativeQuery = true)
    //List<MentorshipApplication> findAllByApplicant(@Param("applicant_username") String username);
    List<MentorshipApplication> findMentorshipApplicationsByApplicant(Users user);

    //List<MentorshipApplication> findByExperienceLikeAndSubjectEquals(String keyword, Subject subject);

    @Query(value = "SELECT * FROM mentorship_application WHERE subject_id = :subject_id AND experience like %:keyword% AND status = 'approved'", nativeQuery = true)
    Set<MentorshipApplication> findByKeywordAndSubject(@Param("keyword") String keyword, @Param("subject_id") Long subject_id);

    /*
        @Query(value = "UPDATE mentorship_application SET status = 'rejected' WHERE applicant_username = ?1 AND subsubject_nameject_id = ?2 ", nativeQuery = true)
        @Modifying
        @Transactional
        void rejectApplication(@Param("applicant_username") String applicant_username, @Param("subject_id") Long subject_id);
    */
    @Query(value = "SELECT * FROM mentorship_application WHERE applicant_username = ?1 ", nativeQuery = true)
    List<MentorshipApplication> findByUsername(String username);

    @Query(value = "INSERT INTO mentorship_application(applicant_username, subject_id, experience, status) VALUES (?1,?2,?3,'open')", nativeQuery = true)
    @Transactional
    @Modifying
    void saveManually(String username, Long subject_id, String experience);
}
















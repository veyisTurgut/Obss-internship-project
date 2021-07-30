package obss.intern.veyis.manageMentorships.repository;

import obss.intern.veyis.manageMentorships.entity.MentorshipApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<MentorshipApplication, Long> {

    @Query(value = "SELECT * FROM mentorship_application",nativeQuery = true)
    List<MentorshipApplication> findAllApplications();

    @Query(value = "select * FROM mentorship_application WHERE applicant_username =?1 AND  subject_name = ?2 AND subsubject_name = ?3",nativeQuery = true)
    MentorshipApplication findByKeys(@Param("applicant_username") String applicant_username,@Param("subject_name") String subject_name, @Param("subsubject_name")String subsubject_name);
}

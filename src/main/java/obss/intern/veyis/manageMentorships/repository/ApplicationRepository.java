package obss.intern.veyis.manageMentorships.repository;

import obss.intern.veyis.manageMentorships.entity.MentorshipApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<MentorshipApplication, Long> {

    @Query(value = "SELECT * FROM mentorship_application",nativeQuery = true)
    List<MentorshipApplication> findAllApplications();
}

package obss.intern.veyis.manageMentorships.repository;

import obss.intern.veyis.manageMentorships.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    @Query(value = "SELECT * FROM SUBJECT WHERE subject_name = ?1 AND subsubject_name=?2", nativeQuery = true)
    Subject findSubject(@Param("subject_name") String subject_name, @Param("subsubject_name") String subsubject_name);

    @Query(value = " DELETE FROM mentorship_application WHERE subject_id = ?1 ; DELETE FROM subject WHERE subject_id = ?1 ", nativeQuery = true)
    @Transactional
    @Modifying
    void deleteSubject(@Param("subject_id") Long id);

}

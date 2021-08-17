package obss.intern.veyis.manageMentorships.repository;

import obss.intern.veyis.manageMentorships.entity.Program;
import obss.intern.veyis.manageMentorships.entity.Subject;
import obss.intern.veyis.manageMentorships.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @see ApplicationRepository
 */
public interface ProgramRepository extends JpaRepository<Program, Long> {


    @Query(value = "SELECT program.* FROM program,subject WHERE mentee_username= ?1 AND mentor_username = ?2 and subject_name = ?3" +
            " AND subsubject_name = ?4 AND program.subject_id = subject.subject_id", nativeQuery = true)
    Program findByKeys(@Param("mentee_username") String mentee_username, @Param("mentor_username") String mentor_username,
                       @Param("subject_name") String subject_name, @Param("subsubject_name") String subsubject_name);

    @Query(value = "SELECT * FROM program WHERE program_id = ?1", nativeQuery = true)
    Program getProgramById(@Param("program_id") Long program_id);

    @Query(value = "SELECT * FROM program ORDER BY program_id DESC LIMIT 1", nativeQuery = true)
    Program getMaxId();

    List<Program> findProgramByMentorEqualsAndSubjectEquals(Users mentor, Subject subject);


    @Query(value = "SELECT * FROM program WHERE mentee_username = ?1", nativeQuery = true)
    List<Program> findProgramByMentee(String mentee);

/*
    @Query(value = "INSERT INTO program (program_id, end_date, is_active, mentee_comment, mentor_comment, start_date, status, mentee_username, mentor_username, subject_id) VALUES " +
            " (?1 , ?2 , ?3 , ?4 , ?5 , ?6 , ?7 , ?8 , ?9 ,?10)", nativeQuery = true)
    @Modifying
    @Transactional
    void saveProgram(Long program_id, Date end_date, Boolean is_active, String mentee_comment, String mentor_comment, Date start_date, String status, String mentee_username, String mentor_username, Long subject_id);
*/
}


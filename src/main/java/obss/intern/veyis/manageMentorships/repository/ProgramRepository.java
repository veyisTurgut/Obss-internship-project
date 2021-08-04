package obss.intern.veyis.manageMentorships.repository;

import obss.intern.veyis.manageMentorships.entity.Program;
import obss.intern.veyis.manageMentorships.entity.Subject;
import obss.intern.veyis.manageMentorships.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProgramRepository extends JpaRepository<Program, Long> {


    @Query(value = "SELECT program.* FROM program,subject WHERE mentee_username= ?1 AND mentor_username = ?2 and subject_name = ?3" +
            " AND subsubject_name = ?4 AND program.subject_id = subject.subject_id", nativeQuery = true)
    Program findByKeys(@Param("mentee_username") String mentee_username, @Param("mentor_username") String mentor_username,
                       @Param("subject_name") String subject_name, @Param("subsubject_name") String subsubject_name);

    @Query(value = "SELECT * FROM program WHERE program_id = ?1", nativeQuery = true)
    Program getProgramById(@Param("program_id") Long program_id);

    @Query(value = "SELECT * FROM program ORDER BY program_id DESC LIMIT 1", nativeQuery = true)
    Program getMaxId();

    /*
    // bir mentor aynı konuda 2 mentee ile çalışabilir.
    @Query(value = "Select Program.* \n" +
            "from \n" +
            "\t(SELECT mentor_username,subject_id, count(*) FROM program group by(mentor_username,subject_id) having count(*) <2 ) S, Program \n" +
            "WHERE program.mentor_username = S.mentor_username AND program.subject_id = S.subject_id;", nativeQuery = true)
    List<Program> findAllActive();
    */

    List<Program> findProgramByMentorEqualsAndSubjectEquals(Users mentor, Subject subject);
}

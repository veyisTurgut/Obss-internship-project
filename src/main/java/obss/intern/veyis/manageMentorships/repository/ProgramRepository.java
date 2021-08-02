package obss.intern.veyis.manageMentorships.repository;

import obss.intern.veyis.manageMentorships.dto.ProgramDTO;
import obss.intern.veyis.manageMentorships.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProgramRepository extends JpaRepository<Program, Long> {


    @Query(value = "SELECT program.* FROM program,subject WHERE mentee_username= ?1 AND mentor_username = ?2 and subject_name = ?3" +
            " AND subsubject_name = ?4 AND program.subject_id = subject.subject_id", nativeQuery = true)
    Program findByKeys(@Param("mentee_username") String mentee_username, @Param("mentor_username") String mentor_username,
                       @Param("subject_name") String subject_name, @Param("subsubject_name") String subsubject_name);


}

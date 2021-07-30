package obss.intern.veyis.manageMentorships.repository;

import obss.intern.veyis.manageMentorships.dto.ProgramDTO;
import obss.intern.veyis.manageMentorships.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProgramRepository extends JpaRepository<Program,Long> {
    @Query(value = "SELECT PROGRAM.programname,PROGRAM.startdate,PROGRAM.enddate,PROGRAM.status FROM \n" +
            " (SELECT program_name FROM ENROLLMENT WHERE mentee_name=?1) E, PROGRAM \n" +
            " WHERE PROGRAM.programname = E.program_name; ", nativeQuery = true)
    List<Program> findAllProgramsMenteed(@Param("username") String username);

    @Query(value = "SELECT PROGRAM.programname,PROGRAM.startdate,PROGRAM.enddate,PROGRAM.status FROM " +
            "PROGRAM,(SELECT mentored_program_name FROM USERS WHERE username=?1)U WHERE U.mentored_program_name = PROGRAM.programname",
            nativeQuery = true)
    Program findProgramMentored(@Param("username") String username);

}

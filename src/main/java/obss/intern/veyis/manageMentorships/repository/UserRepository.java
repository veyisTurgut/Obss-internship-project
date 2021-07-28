package obss.intern.veyis.manageMentorships.repository;

import obss.intern.veyis.manageMentorships.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import obss.intern.veyis.manageMentorships.entity.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);

    @Query(value = "SELECT PROGRAM.programname,PROGRAM.startdate,PROGRAM.enddate,PROGRAM.status FROM \n" +
            " (SELECT program_id FROM ENROLLMENT WHERE mentee_id = ?1) E, PROGRAM \n" +
            " WHERE PROGRAM.id = E.program_id; ", nativeQuery = true)

    List<Program> findAllProgramsMenteed(@Param("user_id")int user_id);
}
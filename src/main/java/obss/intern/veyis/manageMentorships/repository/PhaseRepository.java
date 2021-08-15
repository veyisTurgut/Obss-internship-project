package obss.intern.veyis.manageMentorships.repository;

import obss.intern.veyis.manageMentorships.entity.Phase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import javax.transaction.Transactional;

public interface PhaseRepository extends JpaRepository<Phase, Long> {

    @Query(value = "SELECT phase_id, end_date,expected_end_date, mentee_experience, mentee_point, mentor_experience, mentor_point, start_date, program_id" +
            " FROM phase WHERE  phase_id = ?1 AND program_id = ?2 ", nativeQuery = true)
    Phase getPhaseById(@Param("phase_id") Long phase_id, @Param("program_id") Long program_id);


    @Query(value = "INSERT INTO phase(phase_id, end_date,expected_end_date, mentee_experience, mentee_point, mentor_experience, mentor_point, start_date, program_id)" +
            " VALUES (?2, null,null,null,null,null,null,null, ?1) ", nativeQuery = true)
    @Modifying
    @Transactional
    void addByIds(Long program_id, int phase_id);
}

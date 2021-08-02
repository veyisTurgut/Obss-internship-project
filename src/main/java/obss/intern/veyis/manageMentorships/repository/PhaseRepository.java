package obss.intern.veyis.manageMentorships.repository;

import obss.intern.veyis.manageMentorships.entity.Phase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PhaseRepository extends JpaRepository<Phase, Long> {

    @Query(value = "SELECT * FROM phase WHERE program_id = ?2 AND phase_id = ?1", nativeQuery = true)
    Phase getPhaseById(@Param("phaseid") Long phase_id, @Param("program_id") Long program_id);

}

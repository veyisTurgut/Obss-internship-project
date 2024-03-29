package obss.intern.veyis.manageMentorships.mapper;

import obss.intern.veyis.manageMentorships.dto.PhaseDTO;
import obss.intern.veyis.manageMentorships.entity.Phase;
import obss.intern.veyis.manageMentorships.entity.Program;

import java.util.List;

/**
 * @see ApplicationMapper
 */
//@Mapper(componentModel = "spring")
public interface PhaseMapper {

    PhaseDTO mapToDto(Phase phase);

    Phase mapToEntity(PhaseDTO phaseDTO, Program program);

    List<PhaseDTO> mapToDto(List<Phase> phaseList);

    List<Phase> mapToEntity(List<PhaseDTO> phaseDTOList, Program program);

}

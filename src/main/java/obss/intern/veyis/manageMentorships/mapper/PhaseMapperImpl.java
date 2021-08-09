
package obss.intern.veyis.manageMentorships.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.zip.DataFormatException;

import obss.intern.veyis.manageMentorships.dto.PhaseDTO;
import obss.intern.veyis.manageMentorships.entity.Phase;
import obss.intern.veyis.manageMentorships.entity.Program;
import obss.intern.veyis.manageMentorships.entity.compositeKeys.PhaseKey;
import org.springframework.stereotype.Component;

@Component
public class PhaseMapperImpl implements PhaseMapper {
    public PhaseMapperImpl() {
    }

    public PhaseDTO mapToDto(Phase phase) {
        if (phase == null) {
            return null;
        } else {

            String mentee_experience = phase.getMentee_experience();
            String mentor_experience = phase.getMentor_experience();
            Integer mentee_point = phase.getMentee_point();
            Integer mentor_point = phase.getMentor_point();
            Long program_id = phase.getId().getProgram_id().getProgram_id();
            Date end_date = phase.getEnd_date();
            Date start_date = phase.getStart_date();
            Long phase_id = phase.getId().getPhase_id();
            PhaseDTO phaseDTO = new PhaseDTO(program_id, phase_id, mentee_experience, mentor_experience, start_date, end_date, mentor_point, mentee_point);
            return phaseDTO;
        }
    }

    public Phase mapToEntity(PhaseDTO phaseDTO, Program program) {
        if (phaseDTO == null) {
            return null;
        } else {
            Phase phase = new Phase();
            PhaseKey id = new PhaseKey();
            /**/
            id.setPhase_id(phaseDTO.getPhase_id());
            id.setProgram_id(program.getProgram_id());
            phase.setId(id);
            /**/
            phase.setProgram(program);

            phase.setStart_date(phaseDTO.getStart_date());
            phase.setEnd_date(phaseDTO.getEnd_date());
            phase.setMentee_experience(phaseDTO.getMentee_experience());
            phase.setMentor_experience(phaseDTO.getMentor_experience());
            phase.setMentee_point(phaseDTO.getMentee_point());
            phase.setMentor_point(phaseDTO.getMentor_point());
            return phase;
        }
    }

    public List<PhaseDTO> mapToDto(List<Phase> phaseList) {
        if (phaseList == null) {
            return null;
        } else {
            List<PhaseDTO> list = new ArrayList(phaseList.size());
            Iterator var3 = phaseList.iterator();

            while (var3.hasNext()) {
                Phase phase = (Phase) var3.next();
                list.add(this.mapToDto(phase));
            }

            return list;
        }
    }

    public List<Phase> mapToEntity(List<PhaseDTO> phaseDTOList, Program program) {
        if (phaseDTOList == null) {
            return null;
        } else {
            List<Phase> list = new ArrayList(phaseDTOList.size());
            Iterator var3 = phaseDTOList.iterator();

            while (var3.hasNext()) {
                PhaseDTO phaseDTO = (PhaseDTO) var3.next();
                list.add(this.mapToEntity(phaseDTO, program));
            }

            return list;
        }
    }
}

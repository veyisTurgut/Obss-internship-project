package obss.intern.veyis.manageMentorships.mapper;

import java.util.*;
import java.util.stream.Collectors;

import obss.intern.veyis.manageMentorships.dto.PhaseDTO;
import obss.intern.veyis.manageMentorships.dto.ProgramDTO;
import obss.intern.veyis.manageMentorships.entity.Phase;
import obss.intern.veyis.manageMentorships.entity.Program;
import obss.intern.veyis.manageMentorships.entity.Subject;
import obss.intern.veyis.manageMentorships.entity.Users;
import obss.intern.veyis.manageMentorships.entity.compositeKeys.ProgramId;
import org.springframework.stereotype.Component;

@Component
public class ProgramMapperImpl implements ProgramMapper {
    public ProgramMapperImpl() {
    }

    private final PhaseMapperImpl phaseMapper = new PhaseMapperImpl();

    public ProgramDTO mapToDto(Program program) {
        if (program == null) {
            return null;
        } else {
            Long program_id = program.getProgram_id().getProgram_id();
            Date end_date = program.getEnd_date();
            Date start_date = program.getStart_date();
            String status = program.getStatus();

            /*
            Users mentee = program.getMentee();
            String mentee_username = (mentee == null) ? null : mentee.getUsername();

            Users mentor = program.getMentee();
            String mentor_username = (mentee == null) ? null : mentor.getUsername();
            */

            String mentee_username = (program.getMentee() == null) ? null : program.getMentee().getUsername();
            String mentor_username = program.getMentor().getUsername();
            Set<PhaseDTO> phases = (program.getPhases() == null) ? null : phaseMapper.mapToDto(program.getPhases().stream().collect(Collectors.toList()))
                    .stream().collect(Collectors.toSet());
            String subject_name = program.getSubject().getSubject_name();
            String subsubject_name = program.getSubject().getSubsubject_name();
            String mentee_comment = program.getMentee_comment();
            String mentor_comment = program.getMentor_comment();

            ProgramDTO programDTO = new ProgramDTO(program_id, (Date) end_date, (Date) start_date, (String) status, (String) mentee_username,
                    (String) mentor_username, (String) subsubject_name, (String) subject_name, (Set) phases, mentor_comment, mentee_comment);
            return programDTO;
        }
    }

    public Program mapToEntity(ProgramDTO programDTO, Users Mentor, Users Mentee, Subject subject) {
        if (programDTO == null) {
            return null;
        } else {
            Program program = new Program();
            /**/
            ProgramId id = new ProgramId();
            id.setProgram_id(programDTO.getProgram_id());
            program.setProgram_id(id);
            /**/
            program.setEnd_date(programDTO.getEnd_date());
            program.setMentee(Mentee);
            program.setMentor(Mentor);
            program.setStart_date(new Date(System.currentTimeMillis()));
            program.setMentee_comment(programDTO.getMentee_comment());
            program.setMentor_comment(programDTO.getMentor_comment());
            program.setSubject(subject);
            program.setIs_active(true);
            program.setStatus("Başlamadı");
            return program;

        }
    }

    public List<ProgramDTO> mapToDto(List<Program> programList) {
        if (programList == null) {
            return null;
        } else {
            List<ProgramDTO> list = new ArrayList(programList.size());
            Iterator var3 = programList.iterator();

            while (var3.hasNext()) {
                Program program = (Program) var3.next();
                list.add(this.mapToDto(program));
            }

            return list;
        }
    }

}

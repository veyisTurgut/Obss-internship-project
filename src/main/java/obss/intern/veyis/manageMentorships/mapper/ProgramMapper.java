package obss.intern.veyis.manageMentorships.mapper;

import obss.intern.veyis.manageMentorships.dto.ProgramDTO;
import obss.intern.veyis.manageMentorships.entity.Program;
import obss.intern.veyis.manageMentorships.entity.Subject;
import obss.intern.veyis.manageMentorships.entity.Users;
import java.util.List;

/**
 * @see ApplicationMapper
 */
//@Mapper(componentModel = "spring")
public interface ProgramMapper {

    ProgramDTO mapToDto(Program program);

    Program mapToEntity(ProgramDTO programDTO, Users Mentor, Users Mentee, Subject subject);

    List<ProgramDTO> mapToDto(List<Program> programList);



}

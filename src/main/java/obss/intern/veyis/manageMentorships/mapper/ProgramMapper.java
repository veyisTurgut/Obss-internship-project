package obss.intern.veyis.manageMentorships.mapper;

import obss.intern.veyis.manageMentorships.dto.ProgramDTO;
import obss.intern.veyis.manageMentorships.entity.Phase;
import obss.intern.veyis.manageMentorships.entity.Program;
import obss.intern.veyis.manageMentorships.entity.Subject;
import obss.intern.veyis.manageMentorships.entity.Users;
import org.mapstruct.Mapper;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

//@Mapper(componentModel = "spring")
public interface ProgramMapper {

    ProgramDTO mapToDto(Program program);

    Program mapToEntity(ProgramDTO programDTO, Users Mentor, Users Mentee, Set<Phase> phases, Subject subject);

    List<ProgramDTO> mapToDto(List<Program> programList);



}

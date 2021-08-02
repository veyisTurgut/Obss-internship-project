package obss.intern.veyis.manageMentorships.mapper;

import obss.intern.veyis.manageMentorships.dto.ProgramDTO;
import obss.intern.veyis.manageMentorships.entity.Program;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProgramMapper {

    ProgramDTO mapToDto(Program program);

    Program mapToEntity(ProgramDTO programDTO);

    List<ProgramDTO> mapToDto(List<Program> programList);

    List<Program> mapToEntity(List<ProgramDTO> programDTOList);


}

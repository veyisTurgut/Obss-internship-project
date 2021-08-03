package obss.intern.veyis.manageMentorships.mapper;

import obss.intern.veyis.manageMentorships.dto.SubjectDTO;
import obss.intern.veyis.manageMentorships.entity.Subject;
import org.mapstruct.Mapper;

import java.util.List;

//@Mapper(componentModel = "spring")
public interface SubjectMapper {

    SubjectDTO mapToDto(Subject subject);

    Subject mapToEntity(SubjectDTO subjectDTO);

    List<SubjectDTO> mapToDto(List<Subject> subjectList);

    List<Subject> mapToEntity(List<SubjectDTO> subjectDTOList);

}

package obss.intern.veyis.manageMentorships.mapper;

import obss.intern.veyis.manageMentorships.dto.SubjectDTO;
import obss.intern.veyis.manageMentorships.entity.Subject;

import java.util.List;

/**
 * @see ApplicationMapper
 */
//@Mapper(componentModel = "spring") // no more needed since I manually implemented mappers.
public interface SubjectMapper {

    SubjectDTO mapToDto(Subject subject);

    Subject mapToEntity(SubjectDTO subjectDTO);

    List<SubjectDTO> mapToDto(List<Subject> subjectList);

    List<Subject> mapToEntity(List<SubjectDTO> subjectDTOList);

}

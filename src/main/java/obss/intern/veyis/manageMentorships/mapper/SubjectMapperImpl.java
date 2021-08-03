package obss.intern.veyis.manageMentorships.mapper;

import obss.intern.veyis.manageMentorships.dto.SubjectDTO;
import obss.intern.veyis.manageMentorships.dto.UserDTO;
import obss.intern.veyis.manageMentorships.entity.Subject;
import obss.intern.veyis.manageMentorships.entity.Users;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class SubjectMapperImpl implements SubjectMapper {
    @Override
    public SubjectDTO mapToDto(Subject subject) {
        if (subject == null) {
            return null;
        } else {
            SubjectDTO subjectDTO = new SubjectDTO(subject.getSubject_id(), subject.getSubject_name(), subject.getSubsubject_name());
            return subjectDTO;
        }
    }

    @Override
    public Subject mapToEntity(SubjectDTO subjectDTO) {
        if (subjectDTO == null) {
            return null;
        } else {
            Subject subject = new Subject();
            subject.setSubject_name(subjectDTO.getSubject_name());
            subject.setSubsubject_name(subjectDTO.getSubsubject_name());
            return subject;
        }
    }

    @Override
    public List<SubjectDTO> mapToDto(List<Subject> subjectList) {
        if (subjectList == null) {
            return null;
        } else {
            List<SubjectDTO> list = new ArrayList(subjectList.size());
            Iterator var3 = subjectList.iterator();

            while (var3.hasNext()) {
                Subject subject = (Subject) var3.next();
                list.add(this.mapToDto(subject));
            }
            return list;
        }
    }

    @Override
    public List<Subject> mapToEntity(List<SubjectDTO> subjectDTOList) {
        if (subjectDTOList == null) {
            return null;
        } else {
            List<Subject> list = new ArrayList(subjectDTOList.size());
            Iterator var3 = subjectDTOList.iterator();

            while (var3.hasNext()) {
                SubjectDTO subjectDTO = (SubjectDTO) var3.next();
                list.add(this.mapToEntity(subjectDTO));
            }
            return list;
        }
    }

}

package obss.intern.veyis.service;

import lombok.RequiredArgsConstructor;
import obss.intern.veyis.config.response.MessageResponse;
import obss.intern.veyis.config.response.MessageType;
import obss.intern.veyis.manageMentorships.dto.SubjectDTO;
import obss.intern.veyis.manageMentorships.entity.Subject;
import obss.intern.veyis.manageMentorships.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public List<Subject> getAllSubjects(){ return subjectRepository.findAll();};

    public MessageResponse addSubject(Subject subject) {
        if (subjectRepository.findSubject(subject.getSubject_name(),subject.getSubsubject_name()) != null) {
            return new MessageResponse("Konu özgün olmalı!", MessageType.ERROR);
        }
        subjectRepository.save(subject);
        return new MessageResponse("Konu eklendi!", MessageType.SUCCESS);
    }

    public MessageResponse deleteSubject(SubjectDTO subjectDTO) {
        Subject subject = subjectRepository.findSubject(subjectDTO.getSubject_name(),subjectDTO.getSubsubject_name());
        if (subject== null){
            return new MessageResponse("Zaten yok!", MessageType.ERROR);
        }
        //subjectRepository.delete(subject);
        subjectRepository.deleteSubject(subject.getSubject_id());
        return new MessageResponse("Silindi",MessageType.SUCCESS);
    }

    public MessageResponse deleteSubjectById(Long subject_id) {
        Subject subject = subjectRepository.getById(subject_id);
        if (subject== null){
            return new MessageResponse("Zaten yok!", MessageType.ERROR);
        }
        //subjectRepository.delete(subject);
        subjectRepository.deleteSubject(subject.getSubject_id());
        return new MessageResponse("Silindi",MessageType.SUCCESS);
    }
}

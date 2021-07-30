package obss.intern.veyis.service;

import lombok.RequiredArgsConstructor;
import obss.intern.veyis.manageMentorships.entity.Subject;
import obss.intern.veyis.manageMentorships.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;

    List<Subject> getAllSubjects(){ return subjectRepository.findAll();};
}

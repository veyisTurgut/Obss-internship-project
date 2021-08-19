package obss.intern.veyis.service;

import lombok.RequiredArgsConstructor;
import obss.intern.veyis.config.response.MessageResponse;
import obss.intern.veyis.config.response.MessageType;
import obss.intern.veyis.manageMentorships.entity.MentorshipApplication;
import obss.intern.veyis.manageMentorships.entity.Subject;
import obss.intern.veyis.manageMentorships.repository.ApplicationRepository;
import obss.intern.veyis.manageMentorships.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final ApplicationRepository applicationRepository;

    /**
     * <h1> Get All Subjects -- Service</h1>
     * This function fetches all the subjects in the system via repository class.
     *
     * @return List<Subject>: List of subjects.
     * @see Subject
     */
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAllSorted();
    }

    /**
     * <h1> Get Subjects Of the Applications Of Given User Applied -- Service</h1>
     * This function fetches all the subjects of the applications of a user in the system via repository class.
     *
     * @param username: username of the user
     * @return List<Subject>: List of subjects.
     * @see Subject
     */
    public List<MentorshipApplication> getSubjectsThatAUserApplied(String username) {
        return applicationRepository.findByUsername(username).stream()
                //  .filter(x -> x.getApplicant().getUsername().equals(username))
                .collect(Collectors.toList());
    }

    /**
     * <h1> Get Subjects Of the Applications Of Given User Not Applied  -- Service</h1>
     * This function fetches all the subjects of the applications of a user and all of
     * the subjects in the system via repository class. Then it takes the set difference.
     *
     * @param username: username of the user
     * @return List<Subject>: List of subjects.
     * @see Subject
     */
    public List<Subject> getAllSubjectsExceptAUser(String username) {
        Set<Subject> applied = getSubjectsThatAUserApplied(username).stream().map(x->x.getSubject()).collect(Collectors.toSet());
        Set<Subject> all = subjectRepository.findAll().stream().collect(Collectors.toSet());
        all.removeAll(applied);
        return all.stream().collect(Collectors.toList());
    }


    /**
     * <h1> Create a Subject  -- Service</h1>
     * This function adds a subject record to the database if same is not already there.
     *
     * @param subject: Contents of the subject.
     * @return MessageResponse: SUCCESS upon successful operation, ERROR with an explanation otherwise.
     */
    public MessageResponse addSubject(Subject subject) {
        if (subjectRepository.findSubject(subject.getSubject_name(), subject.getSubsubject_name()) != null) {
            return new MessageResponse("Konu özgün olmalı!", MessageType.ERROR);
        }
        subjectRepository.save(subject);
        return new MessageResponse("Konu eklendi!", MessageType.SUCCESS);
    }

    /**
     * <h1> Delete a Subject By ID  -- Service</h1>
     * This function deletes a subject record from the database. It's not a problem if subject does not exist.
     *
     * @param subject_id: ID of the subject.
     * @return MessageResponse: SUCCESS upon successful operation, ERROR with an explanation otherwise.
     */
    public MessageResponse deleteSubjectById(Long subject_id) {
        try {
            subjectRepository.deleteSubject(subject_id);
        }catch (Exception e){
            return new MessageResponse("Bu konuyla ilgili aktif programlar var. Silinemez!", MessageType.ERROR);
        }
        return new MessageResponse("Silindi", MessageType.SUCCESS);
    }

    /**
     * <h1> Get a Subject By Keys -- Service</h1>
     * This function fetches the subject meeting given requirements in parameter .
     *
     * @param subject_name    Subject name.
     * @param subsubject_name Subsubject name.
     * @return List<Subject>: List of subjects.
     * @see Subject
     */
    public Subject getByKeys(String subject_name, String subsubject_name) {
        return subjectRepository.findSubject(subject_name, subsubject_name);
    }

}

package obss.intern.veyis.service;

import lombok.RequiredArgsConstructor;
import obss.intern.veyis.config.response.MessageResponse;
import obss.intern.veyis.config.response.MessageType;
import obss.intern.veyis.manageMentorships.dto.ApplicationDTO;
import obss.intern.veyis.manageMentorships.entity.MentorshipApplication;
import obss.intern.veyis.manageMentorships.entity.Program;
import obss.intern.veyis.manageMentorships.entity.Subject;
import obss.intern.veyis.manageMentorships.entity.Users;
import obss.intern.veyis.manageMentorships.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ProgramRepository programRepository;
    private final SubjectRepository subjectRepository;


    /**
     * <h1> Apply For A Mentorship -- Service</h1>
     * This function is called from controller class.
     * Since this is an insert operation, parameter application is a brand new object, yet its fields may be null.
     * <p/>
     * First, we check whether applicant and subjects are valid. If not return error, otherwise continue with checking
     * database that whether this application already exists. If exists return error, otherwise save this new application
     * to database and return "SUCCESS".
     *
     * @param application: Contains the information of applicant, subject and experience of applicant upon subject.
     * @return MessageResponse: Returns "SUCCESS" after successful operation or "ERROR" with a reason.
     * @see MentorshipApplication
     * @see MessageResponse
     */
    public MessageResponse addMentorshipApplication(MentorshipApplication application) {
        //first check whether subject and user exists
        if (application.getApplicant() == null) {
            return new MessageResponse("Kullanıcı bulunamadı.", MessageType.ERROR);
        }
        if (application.getSubject() == null) {
            return new MessageResponse("Konu bulunamadı", MessageType.ERROR);
        }
        MentorshipApplication application_from_db = applicationRepository.findAllByKeys(application.getApplicant().getUsername(), application.getSubject().getSubject_id());
        if (application_from_db != null) {
            return new MessageResponse("Başvuru zaten var.", MessageType.ERROR);
        }
        applicationRepository.saveManually(application.getApplicant().getUsername(), application.getSubject().getSubject_id(), application.getExperience());
        return new MessageResponse("Başarılı.", MessageType.SUCCESS);
    }

    /**
     * <h1> Delete a Mentorship -- Service</h1>
     * This function is called from controller class.
     * First we check whether parameters are null. If they are return error, otherwise delete the application.
     *
     * @param subject: Contains the information of the subject.
     * @param mentor:  Contains the information of the applicant.
     * @return MessageResponse: Returns "SUCCESS" after successful operation or "ERROR" with a reason.
     * @see Subject
     * @see Users
     */
    public MessageResponse deleteMentorshipApplication(Subject subject, Users mentor) {
        if (mentor == null || subject == null)
            return new MessageResponse("Hatalı parametreler!", MessageType.ERROR);
        MentorshipApplication application = applicationRepository.findAllByKeys(mentor.getUsername(), subject.getSubject_id());
        applicationRepository.delete(application);
        return new MessageResponse("Silindi!", MessageType.SUCCESS);
    }

    /**
     * <h1> List All Mentorship Applications -- Service</h1>
     * This function is called from controller class.
     * It returns all the applications in the database as a list.
     *
     * @return List<ApplicationDTO>: Returns all the mentorship applications as a list.
     * @see MentorshipApplication
     */
    public List<MentorshipApplication> findAllApplications() {
        return applicationRepository.findAllApplications();
    }

    /**
     * <h1> Filter out Mentorship Applications By Their Status And List Them -- Service</h1>
     * This function is called from controller class.
     * It fetches and returns all the applications in the database whose status equals to the param as a list.
     *
     * @param status: status of the application.
     * @return List<ApplicationDTO>: Returns all the applications in the database whose status equals to the param as a list.
     * @see MentorshipApplication
     */
    public List<MentorshipApplication> findApplicationsByStatus(String status) {
        return applicationRepository.findMentorshipApplicationsByStatusEquals(status);
    }

    /**
     * <h1> List All Mentorship Applications a Mentee Can Enroll -- Service</h1>
     * Users can see all the subjects and their mentors they can enroll.
     * <p/>
     * First we find the subjects that user already enrolled as mentee.
     * Then we find all the approved applications. Set difference of these gives
     * us the applications that user can enroll as a mentee.
     *
     * @param mentee Username of the mentee.
     * @return List<ApplicationDTO>: Returns the subjects and mentors offered that mentee can apply .
     * @see ApplicationDTO
     */
    public List<MentorshipApplication> findSubjectsUserCanEnrollAsAMentee(String mentee) {
        List<Program> users = programRepository.findProgramByMentee(mentee);
        Set<MentorshipApplication> approved = findApplicationsByStatus("approved").stream().collect(Collectors.toSet());
        if (users.size() == 0) {
            approved.stream().collect(Collectors.toList());
        }
        List<Subject> subjects = users.stream().filter(x -> !x.getStatus().equals("ended")).
                map(x -> x.getSubject()).collect(Collectors.toList());
        approved = approved.stream().filter(x -> !subjects.contains(x.getSubject())).collect(Collectors.toSet());

        return approved.stream().collect(Collectors.toList());
    }

    /**
     * <h1> Approve a Mentorship Application -- Service</h1>
     * First we check whether application exists and active. Return error if not. If found, its status will be changed to "approved".
     *
     * @param applicationDTO: Information of the application.
     * @return MessageResponse: SUCCESS upon successful operation, ERROR with an explanation otherwise.
     * @see ApplicationDTO
     */
    public MessageResponse approveMentorshipApplication(ApplicationDTO applicationDTO) {
        MentorshipApplication application_from_db = findFromDto(applicationDTO);
        if (application_from_db == null)
            return new MessageResponse("Başvuru bulunamadı!", MessageType.ERROR);
        if (!application_from_db.getStatus().equals("open"))
            return new MessageResponse("Aktif başvuru bulunamadı!", MessageType.ERROR);
        application_from_db.setStatus("approved");
        applicationRepository.save(application_from_db);
        return new MessageResponse("Başvuru onaylandı.", MessageType.SUCCESS);
    }

    /**
     * <h1> Reject a Mentorship Application -- Service</h1>
     * First we check whether application exists and active. Return error if not. If found, its status will be changed to "rejected".
     *
     * @param applicationDTO: Information of the application.
     * @return MessageResponse: SUCCESS upon successful operation, ERROR with an explanation otherwise.
     * @see ApplicationDTO
     */
    public MessageResponse rejectMentorshipApplication(ApplicationDTO applicationDTO) {
        MentorshipApplication application_from_db = findFromDto(applicationDTO);
        if (application_from_db == null)
            return new MessageResponse("Başvuru bulunamadı!", MessageType.ERROR);
        if (!application_from_db.getStatus().equals("open"))
            return new MessageResponse("Aktif başvuru bulunamadı!", MessageType.ERROR);
        application_from_db.setStatus("rejected");
        applicationRepository.save(application_from_db);
        return new MessageResponse("Başvuru reddedildi.", MessageType.SUCCESS);
    }

    /**
     * <h1> Update a Mentorship Application Experience -- Service</h1>
     * Users can update the experience section of their application.
     * <br/>
     * First we check whether applications exists. Return error if not. If found, its experience will be updated.
     *
     * @param applicationDTO: Information of the application.
     * @return MessageResponse: SUCCESS upon successful operation, ERROR with an explanation otherwise.
     * @see ApplicationDTO
     */
    public MessageResponse updateMentorshipApplication(ApplicationDTO applicationDTO) {
        MentorshipApplication application_from_db = findFromDto(applicationDTO);
        if (application_from_db == null) return new MessageResponse("Başvuru bulunamadı!", MessageType.ERROR);
        application_from_db.setExperience(applicationDTO.getExperience());
        applicationRepository.save(application_from_db);
        return new MessageResponse("Deneyim güncellendi.", MessageType.SUCCESS);
    }

    /**
     * <h1> Helper Function to Find an Application From Database </h1>
     * This function takes the important information of the application as parameter and calls repository class to fetch the application.
     *
     * @param applicationDTO: Information of the application.
     * @return MentorshipApplication object, yet it may be null if none found in the database.
     */
    private MentorshipApplication findFromDto(ApplicationDTO applicationDTO) {
        Subject subject = subjectRepository.findSubject(applicationDTO.getSubject_name(), applicationDTO.getSubsubject_name());
        if (subject == null) return null;
        MentorshipApplication application = applicationRepository.findAllByKeys(applicationDTO.getApplicant_username(), subject.getSubject_id());
        return (application == null) ? null : application;
    }

}
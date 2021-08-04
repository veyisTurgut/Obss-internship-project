package obss.intern.veyis.manageMentorships.mapper;

import obss.intern.veyis.manageMentorships.dto.ApplicationDTO;
import obss.intern.veyis.manageMentorships.entity.MentorshipApplication;
import obss.intern.veyis.manageMentorships.entity.Subject;
import obss.intern.veyis.manageMentorships.entity.Users;

import java.util.List;

public interface ApplicationMapper {


    ApplicationDTO mapToDto(MentorshipApplication mentorshipApplication);

    MentorshipApplication mapToEntity(ApplicationDTO applicationDTO, Subject subject, Users mentor_applicant);

    List<ApplicationDTO> mapToDto(List<MentorshipApplication> mentorshipApplicationList);


}

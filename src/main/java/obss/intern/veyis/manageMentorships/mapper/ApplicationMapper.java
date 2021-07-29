package obss.intern.veyis.manageMentorships.mapper;

import obss.intern.veyis.manageMentorships.dto.ApplicationDTO;
import obss.intern.veyis.manageMentorships.entity.MentorshipApplication;

import java.util.List;

public interface ApplicationMapper {


    ApplicationDTO mapToDto(MentorshipApplication mentorshipApplication);

    MentorshipApplication mapToEntity(ApplicationDTO applicationDTO);

    List<ApplicationDTO> mapToDto(List<MentorshipApplication> mentorshipApplicationList);

    List<MentorshipApplication> mapToEntity(List<ApplicationDTO> applicationDTOList);


}

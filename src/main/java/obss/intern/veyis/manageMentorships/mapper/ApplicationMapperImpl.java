package obss.intern.veyis.manageMentorships.mapper;

import obss.intern.veyis.manageMentorships.dto.ApplicationDTO;
import obss.intern.veyis.manageMentorships.entity.MentorshipApplication;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationMapperImpl implements ApplicationMapper{

    @Override
    public ApplicationDTO mapToDto(MentorshipApplication mentorshipApplication) {
        return null;
    }

    @Override
    public MentorshipApplication mapToEntity(ApplicationDTO applicationDTO) {
        if (applicationDTO == null) {
            return null;
        } else {
            MentorshipApplication application = new MentorshipApplication();
           // application.setApplicant(userService.getUser(applicationDTO.getApplicant_username()));
            application.setExperience(applicationDTO.getExperience());
            //application.setSubject(applicationDTO.getSubject_name(),applicationDTO.getSubsubject_name());
            //application.setSubsubject_name(applicationDTO.getSubsubject_name());
            application.setIs_active(true);
            return application;
        }
    }

    @Override
    public List<ApplicationDTO> mapToDto(List<MentorshipApplication> mentorshipApplicationList) {
        return null;
    }

    @Override
    public List<MentorshipApplication> mapToEntity(List<ApplicationDTO> applicationDTOList) {
        return null;
    }
}

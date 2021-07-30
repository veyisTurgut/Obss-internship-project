package obss.intern.veyis.manageMentorships.mapper;

import obss.intern.veyis.manageMentorships.dto.ApplicationDTO;
import obss.intern.veyis.manageMentorships.dto.UserDTO;
import obss.intern.veyis.manageMentorships.entity.MentorshipApplication;
import obss.intern.veyis.manageMentorships.entity.Users;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ApplicationMapperImpl implements ApplicationMapper {

    @Override
    public ApplicationDTO mapToDto(MentorshipApplication mentorshipApplication) {
        if (mentorshipApplication == null) {
            return null;
        } else {
            ApplicationDTO applicationDTO = new ApplicationDTO(mentorshipApplication.getApplicant().getUsername(),
                    mentorshipApplication.getExperience(),
                    mentorshipApplication.getSubject().getSubject_name(),
                    mentorshipApplication.getSubject().getSubsubject_name());

            return applicationDTO;
        }
    }

    // not working
    @Override
    public MentorshipApplication mapToEntity(ApplicationDTO applicationDTO) {
        if (applicationDTO == null) {
            return null;
        } else {
            MentorshipApplication application = new MentorshipApplication();
            //application.setApplicant(userService.getUser(applicationDTO.getApplicant_username()));
            application.setExperience(applicationDTO.getExperience());
            //application.setSubject(applicationDTO.getSubject_name(),applicationDTO.getSubsubject_name());
            //application.setSubsubject_name(applicationDTO.getSubsubject_name());
            application.setIs_active(true);
            return application;
        }
    }

    @Override
    public List<ApplicationDTO> mapToDto(List<MentorshipApplication> mentorshipApplicationList) {
        if (mentorshipApplicationList == null) {
            return null;
        } else {
            List<ApplicationDTO> list = new ArrayList(mentorshipApplicationList.size());
            Iterator var3 = mentorshipApplicationList.iterator();

            while (var3.hasNext()) {
                MentorshipApplication applications = (MentorshipApplication) var3.next();
                list.add(this.mapToDto(applications));
            }

            return list;
        }
    }

    // not working
    @Override
    public List<MentorshipApplication> mapToEntity(List<ApplicationDTO> applicationDTOList) {
        if (applicationDTOList == null) {
            return null;
        } else {
            List<MentorshipApplication> list = new ArrayList(applicationDTOList.size());
            Iterator var3 = applicationDTOList.iterator();

            while (var3.hasNext()) {
                ApplicationDTO applicationDTO = (ApplicationDTO) var3.next();
                list.add(this.mapToEntity(applicationDTO));
            }

            return list;
        }
    }
}

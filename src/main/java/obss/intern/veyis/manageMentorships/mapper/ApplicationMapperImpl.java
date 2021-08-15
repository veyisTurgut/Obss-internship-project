package obss.intern.veyis.manageMentorships.mapper;

import obss.intern.veyis.manageMentorships.dto.ApplicationDTO;
import obss.intern.veyis.manageMentorships.entity.MentorshipApplication;
import obss.intern.veyis.manageMentorships.entity.Subject;
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
            ApplicationDTO applicationDTO = new ApplicationDTO(
                    mentorshipApplication.getApplicant().getUsername(),

                    mentorshipApplication.getSubject().getSubject_id(),

                    mentorshipApplication.getSubject().getSubject_name(),
                    mentorshipApplication.getSubject().getSubsubject_name(),
                    mentorshipApplication.getExperience()
            );

            return applicationDTO;
        }
    }

    // not working
    @Override
    public MentorshipApplication mapToEntity(ApplicationDTO applicationDTO, Subject subject, Users mentor_applicant) {
        if (applicationDTO == null) {
            return null;
        } else {
            MentorshipApplication application = new MentorshipApplication();
            application.setApplicant(mentor_applicant);
            application.setExperience(applicationDTO.getExperience());
            application.setSubject(subject);
            application.setSubject_name(subject.getSubject_name());
            application.setSubsubject_name(subject.getSubsubject_name());
            application.setStatus("open");
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

}

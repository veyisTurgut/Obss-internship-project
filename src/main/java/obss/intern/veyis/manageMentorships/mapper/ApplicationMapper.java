package obss.intern.veyis.manageMentorships.mapper;

import obss.intern.veyis.manageMentorships.dto.ApplicationDTO;
import obss.intern.veyis.manageMentorships.entity.MentorshipApplication;
import obss.intern.veyis.manageMentorships.entity.Subject;
import obss.intern.veyis.manageMentorships.entity.Users;

import java.util.List;

/**
 * This interface defines the methods that the class mapping MentorshipApplication and its DTO to each other must have.
 * List version of mapToEntity is not exists due to hardness of supplying necessary parameters.
 * Yet this version works properly.
 * <p/>
 * Actually I should not have coded manual implementation of these classes, instead Spring should have implemented
 * it automatically upon seeing //@Mapper(componentModel = "spring")// annotation.
 * Yet it failed to do so. So, I implemented these methods manually.
 *
 * @see ApplicationMapperImpl
 */
public interface ApplicationMapper {

    ApplicationDTO mapToDto(MentorshipApplication mentorshipApplication);

    MentorshipApplication mapToEntity(ApplicationDTO applicationDTO, Subject subject, Users mentor_applicant);

    List<ApplicationDTO> mapToDto(List<MentorshipApplication> mentorshipApplicationList);


}

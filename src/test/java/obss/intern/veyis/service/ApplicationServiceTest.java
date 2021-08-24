package obss.intern.veyis.service;

import obss.intern.veyis.config.response.MessageResponse;
import obss.intern.veyis.config.response.MessageType;
import obss.intern.veyis.manageMentorships.entity.MentorshipApplication;
import obss.intern.veyis.manageMentorships.entity.Program;
import obss.intern.veyis.manageMentorships.entity.Subject;
import obss.intern.veyis.manageMentorships.entity.Users;
import obss.intern.veyis.manageMentorships.repository.ApplicationRepository;
import obss.intern.veyis.manageMentorships.repository.ProgramRepository;
import obss.intern.veyis.manageMentorships.repository.SubjectRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
public class ApplicationServiceTest {

    private ApplicationService applicationService;

    private ApplicationRepository applicationRepository;
    private ProgramRepository programRepository;
    private SubjectRepository subjectRepository;

    @Before
    public void setUp() throws Exception {
        applicationRepository = Mockito.mock(ApplicationRepository.class);
        programRepository = Mockito.mock(ProgramRepository.class);
        subjectRepository = Mockito.mock(SubjectRepository.class);

        applicationService = new ApplicationService(applicationRepository, programRepository, subjectRepository);
    }

    @Test
    public void whenAddMentorshipApplicationCalledWithValidRequest_itShouldReturnSuccess() {
        Users applicant = new Users();
        applicant.setUsername("veyis_t");

        Subject subject = new Subject();
        subject.setSubject_id(1L);
        subject.setSubject_name("star wars");
        subject.setSubsubject_name("clone wars");

        MentorshipApplication application = new MentorshipApplication();
        application.setApplicant(applicant);
        application.setExperience("very well I know, clone wars.");
        application.setStatus("open");
        application.setSubject(subject);

        Mockito.when(applicationRepository.findAllByKeys(application.getApplicant().getUsername(), application.getSubject().getSubject_id())).thenReturn(null);
        Mockito.doNothing().when(applicationRepository).saveManually(application.getApplicant().getUsername(), application.getSubject().getSubject_id(), application.getExperience(), application.getSubject().getSubject_name(), application.getSubject().getSubsubject_name());

        MessageResponse result = applicationService.addMentorshipApplication(application);
        Assert.assertEquals(result, new MessageResponse("Başarılı.", MessageType.SUCCESS));
        Mockito.verify(applicationRepository).findAllByKeys(application.getApplicant().getUsername(), application.getSubject().getSubject_id());
        Mockito.verify(applicationRepository).saveManually(application.getApplicant().getUsername(), application.getSubject().getSubject_id(), application.getExperience(), application.getSubject().getSubject_name(), application.getSubject().getSubsubject_name());

    }

    @Test
    public void whenAddMentorshipApplicationCalledWithValidRequest_itShouldReturnFail() {
        Users applicant = new Users();
        applicant.setUsername("veyis_t");

        Subject subject = new Subject();
        subject.setSubject_id(1L);
        subject.setSubject_name("star wars");
        subject.setSubsubject_name("clone wars");

        MentorshipApplication application = new MentorshipApplication();
        application.setApplicant(applicant);
        application.setExperience("very well I know, clone wars.");
        application.setStatus("open");
        application.setSubject(subject);

        Mockito.when(applicationRepository.findAllByKeys(application.getApplicant().getUsername(), application.getSubject().getSubject_id())).thenReturn(application);

        MessageResponse result = applicationService.addMentorshipApplication(application);
        Assert.assertEquals(result, new MessageResponse("Başvuru zaten var.", MessageType.ERROR));
        Mockito.verify(applicationRepository).findAllByKeys(application.getApplicant().getUsername(), application.getSubject().getSubject_id());

    }

    @Test
    public void whenAddMentorshipApplicationCalledWithInvalidRequest_itShouldReturnFailApplicantNotFound() {
        Subject subject = new Subject();
        subject.setSubject_id(1L);
        subject.setSubject_name("star wars");
        subject.setSubsubject_name("clone wars");

        MentorshipApplication application = new MentorshipApplication();
        application.setApplicant(null);
        application.setExperience("very well I know, clone wars.");
        application.setStatus("open");
        application.setSubject(subject);

        MessageResponse result = applicationService.addMentorshipApplication(application);

        Assert.assertEquals(result, new MessageResponse("Kullanıcı bulunamadı.", MessageType.ERROR));

    }

    @Test
    public void whenAddMentorshipApplicationCalledWithInvalidRequest_itShouldReturnSubjectNotFound() {
        Users applicant = new Users();
        applicant.setUsername("veyis_t");

        MentorshipApplication application = new MentorshipApplication();
        application.setApplicant(applicant);
        application.setExperience("very well I know, clone wars.");
        application.setStatus("open");
        application.setSubject(null);

        MessageResponse result = applicationService.addMentorshipApplication(application);
        Assert.assertEquals(result, new MessageResponse("Konu bulunamadı", MessageType.ERROR));
    }

}
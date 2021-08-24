package obss.intern.veyis.service;

import obss.intern.veyis.config.response.MessageResponse;
import obss.intern.veyis.config.response.MessageType;
import obss.intern.veyis.manageMentorships.entity.MentorshipApplication;
import obss.intern.veyis.manageMentorships.entity.Program;
import obss.intern.veyis.manageMentorships.entity.Subject;
import obss.intern.veyis.manageMentorships.entity.Users;
import obss.intern.veyis.manageMentorships.repository.ApplicationRepository;
import obss.intern.veyis.manageMentorships.repository.PhaseRepository;
import obss.intern.veyis.manageMentorships.repository.ProgramRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ProgramServiceTest {
    private ProgramService programService;

    private ProgramRepository programRepository;
    private PhaseRepository phaseRepository;
    private ApplicationRepository applicationRepository;

    @Before
    public void setUp() throws Exception {
        applicationRepository = Mockito.mock(ApplicationRepository.class);
        programRepository = Mockito.mock(ProgramRepository.class);
        phaseRepository = Mockito.mock(PhaseRepository.class);

        programService = new ProgramService(programRepository, phaseRepository, applicationRepository);
    }

    @Test
    public void whenAddProgramCalledWithValidRequest_itShouldReturnSuccess() {
        Users mentee = new Users();
        mentee.setUsername("veyis_t");
        Users mentor = new Users();
        mentor.setUsername("veyis_t3");

        Subject subject = new Subject();
        subject.setSubject_id(1L);
        subject.setSubject_name("algorithms");
        subject.setSubsubject_name("graph");

        Program program = new Program();
        program.setSubject(subject);
        program.setMentee(mentee);
        program.setMentor(mentor);

        Users[] mock_mentors = new Users[3];
        for (int i = 0; i < 3; i++) {
            Users mock_mentor = new Users();
            mock_mentor.setUsername("veyis_t" + (i + 1));
            mock_mentors[i] = mock_mentor;
        }
        Subject[] mock_subjects = new Subject[5];
        String[][] subject_names = {{"java", "oop"}, {"java", "stream"}, {"algorithms", "greedy"}, {"algorithms", "graph"}, {"matlab", "image_processing"}};
        for (int i = 0; i < 5; i++) {
            Subject mock_subject = new Subject();
            mock_subject.setSubject_id((long) i);
            mock_subject.setSubject_name(subject_names[i][0]);
            mock_subject.setSubsubject_name(subject_names[i][1]);
            mock_subjects[i] = mock_subject;
        }
        List<Program> programs_of_this_mentee = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Program mock_program = new Program();
            mock_program.setMentee(mentee);
            mock_program.setMentor(mock_mentors[i]);
            mock_program.setSubject(mock_subjects[i]);
            programs_of_this_mentee.add(mock_program);
        }
        List<Program> programs_of_this_mentor = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Program mock_program = new Program();
            mock_program.setMentee(mentee);
            mock_program.setMentor(mock_mentors[i]);
            mock_program.setSubject(mock_subjects[i]);
            programs_of_this_mentor.add(mock_program);
        }

        Mockito.when(programRepository.findByKeys(program.getMentee().getUsername(), program.getMentor().getUsername(), program.getSubject().getSubject_name(), program.getSubject().getSubsubject_name())).thenReturn(null);
        Mockito.when(programRepository.findProgramByMentee(program.getMentee().getUsername())).thenReturn(programs_of_this_mentee);
        Mockito.when(programRepository.save(program)).thenReturn(program);

        MessageResponse result = programService.addProgram(program);
        Assert.assertEquals(result, new MessageResponse("Eklendi.", MessageType.SUCCESS));
        Mockito.verify(programRepository).findByKeys(program.getMentee().getUsername(), program.getMentor().getUsername(), program.getSubject().getSubject_name(), program.getSubject().getSubsubject_name());

        Mockito.verify(programRepository).findProgramByMentee(program.getMentee().getUsername());
        Mockito.verify(programRepository).save(program);
    }

    @Test
    public void whenAddProgramCalledWithValidRequest_itShouldReturnErrorSameSubjectDifferentMentor() {
        Users mentee = new Users();
        mentee.setUsername("veyis_t");
        Users mentor = new Users();
        mentor.setUsername("veyis_t2");

        Subject subject = new Subject();
        subject.setSubject_id(1L);
        subject.setSubject_name("algorithms");
        subject.setSubsubject_name("graph");

        Program program = new Program();
        program.setSubject(subject);
        program.setMentee(mentee);
        program.setMentor(mentor);

        Users[] mock_mentors = new Users[3];
        for (int i = 0; i < 3; i++) {
            Users mock_mentor = new Users();
            mock_mentor.setUsername("veyis_t" + (i + 1));
            mock_mentors[i] = mock_mentor;
        }
        Subject[] mock_subjects = new Subject[5];
        String[][] subject_names = {{"java", "oop"}, {"java", "stream"}, {"algorithms", "greedy"}, {"algorithms", "graph"}, {"matlab", "image_processing"}};
        for (int i = 0; i < 5; i++) {
            Subject mock_subject = new Subject();
            mock_subject.setSubject_id((long) i);
            mock_subject.setSubject_name(subject_names[i][0]);
            mock_subject.setSubsubject_name(subject_names[i][1]);
            mock_subjects[i] = mock_subject;
        }
        List<Program> programs_of_this_mentee = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Program mock_program = new Program();
            mock_program.setMentee(mentee);
            mock_program.setMentor(mock_mentors[i]);
            mock_program.setSubject(mock_subjects[i]);
            programs_of_this_mentee.add(mock_program);
        }

        Mockito.when(programRepository.findByKeys(program.getMentee().getUsername(), program.getMentor().getUsername(), program.getSubject().getSubject_name(), program.getSubject().getSubsubject_name())).thenReturn(null);
        Mockito.when(programRepository.findProgramByMentee(program.getMentee().getUsername())).thenReturn(programs_of_this_mentee);

        MessageResponse result = programService.addProgram(program);
        Assert.assertEquals(result, new MessageResponse("Aynı ana konu için sadece 1 mentor ile çalışabilirsin.", MessageType.ERROR));
        Mockito.verify(programRepository).findByKeys(program.getMentee().getUsername(), program.getMentor().getUsername(), program.getSubject().getSubject_name(), program.getSubject().getSubsubject_name());
        Mockito.verify(programRepository).findProgramByMentee(program.getMentee().getUsername());
    }

    @Test
    public void whenAddProgramCalledWithValidRequest_itShouldReturnErrorMentorAlreadyWorksWithTwoMentees() {
        Users mentee = new Users();
        mentee.setUsername("veyis_t");
        Users mentor = new Users();
        mentor.setUsername("lana_del");

        Subject subject = new Subject();
        subject.setSubject_id(1L);
        subject.setSubject_name("star wars");
        subject.setSubsubject_name("clone wars");

        Program program = new Program();
        program.setSubject(subject);
        program.setMentee(mentee);
        program.setMentor(mentor);

        Users[] mock_mentors = new Users[3];
        for (int i = 0; i < 3; i++) {
            Users mock_mentor = new Users();
            mock_mentor.setUsername("veyis_t" + (i + 1));
            mock_mentors[i] = mock_mentor;
        }
        Subject[] mock_subjects = new Subject[5];
        String[][] subject_names = {{"java", "oop"}, {"java", "stream"}, {"algorithms", "greedy"}, {"algorithms", "graph"}, {"matlab", "image_processing"}};
        for (int i = 0; i < 5; i++) {
            Subject mock_subject = new Subject();
            mock_subject.setSubject_id((long) i);
            mock_subject.setSubject_name(subject_names[i][0]);
            mock_subject.setSubsubject_name(subject_names[i][1]);
            mock_subjects[i] = mock_subject;
        }
        List<Program> programs_of_this_mentee = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Program mock_program = new Program();
            mock_program.setMentee(mentee);
            mock_program.setMentor(mock_mentors[i]);
            mock_program.setSubject(mock_subjects[i]);
            programs_of_this_mentee.add(mock_program);
        }
        List<Program> programs_of_this_mentor = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Program mock_program = new Program();
            mock_program.setMentee(mock_mentors[i]);
            mock_program.setMentor(mentor);
            mock_program.setSubject(subject);
            programs_of_this_mentor.add(mock_program);
        }

        Mockito.when(programRepository.findByKeys(program.getMentee().getUsername(), program.getMentor().getUsername(), program.getSubject().getSubject_name(), program.getSubject().getSubsubject_name())).thenReturn(null);
        Mockito.when(programRepository.findProgramByMentee(program.getMentee().getUsername())).thenReturn(programs_of_this_mentee);
        Mockito.when(programRepository.findProgramByMentorEqualsAndSubjectEquals(program.getMentor(), program.getSubject())).thenReturn(programs_of_this_mentor);

        MessageResponse result = programService.addProgram(program);
        Assert.assertEquals(result, new MessageResponse("Mentor zaten 2 kişi ile çalışıyor!", MessageType.ERROR));
        Mockito.verify(programRepository).findByKeys(program.getMentee().getUsername(), program.getMentor().getUsername(), program.getSubject().getSubject_name(), program.getSubject().getSubsubject_name());
        Mockito.verify(programRepository).findProgramByMentee(program.getMentee().getUsername());
        Mockito.verify(programRepository).findProgramByMentorEqualsAndSubjectEquals(program.getMentor(), program.getSubject());

    }

    @Test
    public void whenAddProgramCalledWithInvalidRequest_itShouldReturnSubjectNotFound() {
        Users mentor = new Users();
        mentor.setUsername("veyis_t");

        Subject subject = new Subject();
        subject.setSubject_id(1L);
        subject.setSubject_name("algorithms");
        subject.setSubsubject_name("graph");

        Program program = new Program();
        program.setMentor(mentor);
        program.setSubject(subject);
        MessageResponse result = programService.addProgram(program);
        Assert.assertEquals(result, new MessageResponse("Json hatalı!", MessageType.ERROR));
    }

    @Test
    public void whenAddProgramCalledWithInvalidRequest_itShouldReturnMentorMustBeDifferentFromMentee() {
        Users mentor = new Users();
        mentor.setUsername("veyis_t");

        Subject subject = new Subject();
        subject.setSubject_id(1L);
        subject.setSubject_name("algorithms");
        subject.setSubsubject_name("graph");

        Program program = new Program();
        program.setMentor(mentor);
        program.setMentee(mentor);
        program.setSubject(subject);
        MessageResponse result = programService.addProgram(program);
        Assert.assertEquals(result, new MessageResponse("Mentor ile mentee aynı kişi olamaz!", MessageType.ERROR));
    }
}

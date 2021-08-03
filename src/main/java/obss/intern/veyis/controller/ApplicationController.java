package obss.intern.veyis.controller;

import lombok.RequiredArgsConstructor;
import obss.intern.veyis.config.response.MessageResponse;
import obss.intern.veyis.config.response.MessageType;
import obss.intern.veyis.manageMentorships.dto.ApplicationDTO;
import obss.intern.veyis.manageMentorships.entity.MentorshipApplication;
import obss.intern.veyis.manageMentorships.entity.Subject;
import obss.intern.veyis.manageMentorships.mapper.ApplicationMapper;
import obss.intern.veyis.manageMentorships.mapper.ApplicationMapperImpl;
import obss.intern.veyis.manageMentorships.mapper.UserMapperImpl;
import obss.intern.veyis.service.ApplicationService;
import obss.intern.veyis.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final ApplicationMapperImpl applicationMapper;


    @PostMapping()//user
    public MessageResponse applyForMentorship(@RequestBody @Validated ApplicationDTO applicationDTO) {
        return applicationService.addMentorshipApplication(applicationMapper.mapToEntity(applicationDTO));
    }

    @GetMapping("/all")//user-admin
    public List<ApplicationDTO> allApplications() {
        return applicationMapper.mapToDto(applicationService.findAllApplications());
    }

    @PutMapping("/reject")//admin
    public MessageResponse rejectMentorshipApplication(@RequestBody @Validated ApplicationDTO applicationDTO) {
        return applicationService.rejectMentorshipApplication(applicationDTO);
    }

    @PutMapping("/update")//user
    public MessageResponse updateMentorshipApplication(@RequestBody @Validated ApplicationDTO applicationDTO) {
        return applicationService.updateMentorshipApplication(applicationDTO);
    }

    @PutMapping("/approve")//admin
    public MessageResponse approveMentorshipApplication(@RequestBody @Validated ApplicationDTO applicationDTO) {
        return applicationService.approveMentorshipApplication(applicationDTO);
    }
}

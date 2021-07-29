package obss.intern.veyis.controller;

import lombok.RequiredArgsConstructor;
import obss.intern.veyis.config.response.MessageResponse;
import obss.intern.veyis.config.response.MessageType;
import obss.intern.veyis.manageMentorships.dto.ApplicationDTO;
import obss.intern.veyis.manageMentorships.entity.MentorshipApplication;
import obss.intern.veyis.manageMentorships.mapper.ApplicationMapper;
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
    private final ApplicationMapper applicationMapper;

    /*
    @PostMapping("/applyForMentorship")
    public MessageResponse applyForMentorship(@RequestBody @Valid ApplicationDTO applicationDTO){
        MessageResponse application = applicationService.addMentorshipApplication(applicationMapper.mapToEntity(applicationDTO));
        return application;
    }
    @GetMapping("/all")
    public List<MentorshipApplication> allApplications(){
        return applicationService.findAllApplications();
    }*/
}

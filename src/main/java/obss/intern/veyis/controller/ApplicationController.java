package obss.intern.veyis.controller;

import lombok.RequiredArgsConstructor;
import obss.intern.veyis.config.response.MessageResponse;
import obss.intern.veyis.config.response.MessageType;
import obss.intern.veyis.manageMentorships.entity.MentorshipApplication;
import obss.intern.veyis.service.ApplicationService;
import obss.intern.veyis.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/applyForMentorship/{subject_id}/{subsubject_id}")
    public MessageResponse applyForMentorship(@PathVariable Integer subject_id, @PathVariable Integer subsubject_id){
        return new MessageResponse("", MessageType.SUCCESS);
    }
    @GetMapping("/all")
    public List<MentorshipApplication> allApplications(){
        return applicationService.findAllApplications();
    }
}

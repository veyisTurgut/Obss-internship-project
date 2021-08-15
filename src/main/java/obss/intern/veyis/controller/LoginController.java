package obss.intern.veyis.controller;

import lombok.RequiredArgsConstructor;
import obss.intern.veyis.config.response.MessageResponse;
import obss.intern.veyis.config.response.MessageType;
//import obss.intern.veyis.config.security.SessionManager;
import obss.intern.veyis.manageMentorships.dto.UserDTO;
import obss.intern.veyis.manageMentorships.entity.Admin;
import obss.intern.veyis.manageMentorships.entity.MentorshipApplication;
import obss.intern.veyis.manageMentorships.entity.Users;
import obss.intern.veyis.manageMentorships.mapper.UserMapperImpl;
import obss.intern.veyis.service.AdminService;
import obss.intern.veyis.service.UserService;
import obss.intern.veyis.util.EmailSender;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Collections;
import java.util.Set;

@RequiredArgsConstructor
@Validated
@CrossOrigin
@RestController
@RequestMapping("/login")
public class LoginController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final AdminService adminService;

    @PostMapping("/google")
    public MessageResponse googleLogin(@RequestBody Admin admin) {
        Admin admin_from_db = adminService.findByNameAndMail(admin.getUsername(), admin.getGmail());
        Users user_from_db = userService.findByNameAndMail(admin.getUsername(), admin.getGmail());
        System.out.println(admin.getUsername()+" "+ admin.getGmail());
        if (admin_from_db == null && user_from_db == null) {//new user
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            Users new_user = new Users();
            new_user.setUsername(admin.getUsername());
            new_user.setGmail(admin.getGmail());
            new_user.setPassword(encoder.encode(admin.getGmail()));//set gmail as password, since google login does not provide password but a unique mail
            new_user.setApplicationSet(Collections.emptySet());
            new_user.setProgramsMenteed(Collections.emptySet());
            new_user.setProgramsMentored(Collections.emptySet());
            try {
                userService.addUser(new_user);
            } catch (DataIntegrityViolationException e) {//violates db constraints
                return new MessageResponse("Hatalı bilgiler!", MessageType.ERROR);
            }
            return new MessageResponse("ADMI " +
                    Base64.getEncoder().encodeToString((new_user.getUsername() + ":" + new_user.getPassword()).getBytes()), MessageType.SUCCESS);
        } else if (admin_from_db != null) {// existing admin
            return new MessageResponse("ADMI " +
                    Base64.getEncoder().encodeToString((admin_from_db.getUsername() + ":" + admin_from_db.getPassword()).getBytes()), MessageType.SUCCESS);
        } else {//existing user
            return new MessageResponse("USER " +
                    Base64.getEncoder().encodeToString((user_from_db.getUsername() + ":" + user_from_db.getPassword()).getBytes()), MessageType.SUCCESS);
        }
    }

    @PostMapping()
    public MessageResponse login(@RequestBody Admin admin) throws Exception {
        Authentication auth = null;
        try {
            auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(admin.getUsername(), admin.getPassword()));
            System.out.println(auth);
        } catch (BadCredentialsException e) {
            return new MessageResponse("Bilgiler hatalı", MessageType.ERROR);
        }
        String user_type = "";
        if (auth.getAuthorities().toString().contains("USERS")) {
            user_type = "USER";
        } else if (auth.getAuthorities().toString().contains("ADMINS")) {
            user_type = "ADMI";
        }

        System.out.println(Base64.getEncoder().encodeToString((admin.getUsername() + ":" + admin.getPassword()).getBytes()));
        return new MessageResponse(user_type + " " + Base64.getEncoder().encodeToString((admin.getUsername() + ":" + admin.getPassword()).getBytes()), MessageType.SUCCESS);
    }

    @GetMapping("")
    public String lo(){


        return "zdd";
    }

}

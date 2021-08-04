package obss.intern.veyis.controller;

import lombok.RequiredArgsConstructor;
import obss.intern.veyis.config.MyUserDetailsService;
import obss.intern.veyis.config.response.MessageResponse;
import obss.intern.veyis.config.response.MessageType;
//import obss.intern.veyis.config.security.SessionManager;
import obss.intern.veyis.manageMentorships.entity.Admin;
import obss.intern.veyis.manageMentorships.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.HashSet;

@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
public class LoginController {
    private final AuthenticationManager authenticationManager;
    //private final MyUserDetailsService myUserDetailsService;
    //private JwtTokenUtil jwtTokenUtil;
    //private final SessionManager sessionManager;


    @PostMapping("/login")
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
        } else if (auth.getAuthorities().toString().contains("ANONYMOUS")) {// TODO
            user_type = "ANON";
        }
        System.out.println(Base64.getEncoder().encodeToString((admin.getUsername() + ":" + admin.getPassword()).getBytes()));
        return new MessageResponse(user_type + " " + Base64.getEncoder().encodeToString((admin.getUsername() + ":" + admin.getPassword()).getBytes()), MessageType.SUCCESS);
    }


}

package obss.intern.veyis.controller;

import lombok.RequiredArgsConstructor;
import obss.intern.veyis.config.MyUserDetailsService;
import obss.intern.veyis.config.response.MessageResponse;
import obss.intern.veyis.config.response.MessageType;
//import obss.intern.veyis.config.security.SessionManager;
import obss.intern.veyis.manageMentorships.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public String createAuthenticationToken(@RequestBody Admin admin) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(admin.getUsername(), admin.getPassword())
            );
        } catch (BadCredentialsException e) {
            return "Hatalı kullanıcı adı ya da şifre";
            //throw new Exception("Hatalı kullanıcı adı ya da şifre");
        }
        /*
        String sessionId = sessionManager.generateSesionId(admin.getUsername(), admin.getPassword());
        return sessionId;
        */
        //final UserDetails userDetails = myUserDetailsService.loadUserByUsername(admin.getUsername());
        //final String sessionToken = Token
        SecurityContext context = SecurityContextHolder.getContext();
        String details = context.getAuthentication().getDetails().toString();
        String session_id = details.substring(details.indexOf("SessionId") + 10, details.indexOf("]"));
        System.out.println(context.toString());
        //sessionManager.addSession(session_id);
        return session_id;


    }
/*
    @PostMapping("/logout")
    public MessageResponse logout(String sessionId) {
        return (sessionManager.removeSession(sessionId)) ? new MessageResponse("Logout successful!", MessageType.SUCCESS) : new MessageResponse("Logout failed!", MessageType.ERROR);
    }

    @GetMapping("/activeSessions")
    public HashSet<String> activeSessions(){
        return sessionManager.getActiveSessionIds();
    }


 */
}

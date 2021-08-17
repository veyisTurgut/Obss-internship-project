package obss.intern.veyis.controller;

import lombok.RequiredArgsConstructor;
import obss.intern.veyis.config.response.MessageResponse;
import obss.intern.veyis.config.response.MessageType;
import obss.intern.veyis.manageMentorships.entity.Admin;
import obss.intern.veyis.manageMentorships.entity.Users;
import obss.intern.veyis.service.AdminService;
import obss.intern.veyis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.naming.Name;

import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;

import java.util.Base64;
import java.util.Collections;

@RequiredArgsConstructor
@Validated
@CrossOrigin
@RestController
@RequestMapping("/login")
public class LoginController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final AdminService adminService;

    /**
     * <h1> Login With Google</h1>
     * This endpoint is called upon successful login in frontend server. So we already know that credentials given
     * in request body are valid.
     * <br/>
     * First check whether this user has logged in before. If this is a new one, create a corresponding record in the database.
     * If this was an admin or user, return basic authorization code.
     * <br/>
     * <a href="https://en.wikipedia.org/wiki/Basic_access_authentication"> Click for further information about BasicAuth: </a>
     *
     * @param admin Credentials of logged in person.
     * @return MessageResponse: SUCCESS with basic auth code or ERROR with reason.
     */
    @PostMapping("/google")
    public MessageResponse googleLogin(@RequestBody Admin admin) {
        Admin admin_from_db = adminService.findByNameAndMail(admin.getUsername(), admin.getGmail());
        Users user_from_db = userService.findByNameAndMail(admin.getUsername(), admin.getGmail());
        if (admin_from_db == null && user_from_db == null) {//new user
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            Users new_user = new Users();
            new_user.setUsername(admin.getUsername());
            new_user.setGmail(admin.getGmail());
            new_user.setPassword(encoder.encode(admin.getGmail().substring(0, admin.getGmail().indexOf('@'))));
            //set gmail address as password since google login does not provide password but a unique mail
            new_user.setApplicationSet(Collections.emptySet());
            new_user.setProgramsMenteed(Collections.emptySet());
            new_user.setProgramsMentored(Collections.emptySet());
            try {
                userService.addUser(new_user);
            } catch (DataIntegrityViolationException e) {//violates db constraints
                return new MessageResponse("Hatalı bilgiler!", MessageType.ERROR);
            }
            return new MessageResponse("ADMI " +
                    Base64.getEncoder().encodeToString((new_user.getUsername() + ":" + admin.getGmail().substring(0, admin.getGmail().indexOf('@'))).getBytes()), MessageType.SUCCESS);
        } else if (admin_from_db != null) {// existing admin
            return new MessageResponse("ADMI " +
                    Base64.getEncoder().encodeToString((admin_from_db.getUsername() + ":" + admin.getGmail().substring(0, admin.getGmail().indexOf('@'))).getBytes()), MessageType.SUCCESS);
        } else {//existing user
            return new MessageResponse("USER " +
                    Base64.getEncoder().encodeToString((user_from_db.getUsername() + ":" + admin.getGmail().substring(0, admin.getGmail().indexOf('@'))).getBytes()), MessageType.SUCCESS);
        }
    }

    /**
     * <h1> Ldap Login</h1>
     * This endpoint is called to get BasicAuth token. All users must be already defined in the system in order to login.
     * Ldap is used for authentication.
     *
     * @param admin Credentials of logged in person.
     * @return MessageResponse: SUCCESS with basic auth code or ERROR with reason.
     */
    @PostMapping()
    public MessageResponse login(@RequestBody Admin admin) {
        Authentication auth = null;
        try {
            auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(admin.getUsername(), admin.getPassword()));
        } catch (BadCredentialsException e) {
            return new MessageResponse("Bilgiler hatalı", MessageType.ERROR);
        }
        String user_type = "";
        if (auth.getAuthorities().toString().contains("USERS")) {
            user_type = "USER";
        } else if (auth.getAuthorities().toString().contains("ADMINS")) {
            user_type = "ADMI";
        }
        return new MessageResponse(user_type + " " + Base64.getEncoder().encodeToString((admin.getUsername() + ":" + admin.getPassword()).getBytes()), MessageType.SUCCESS);
    }

}

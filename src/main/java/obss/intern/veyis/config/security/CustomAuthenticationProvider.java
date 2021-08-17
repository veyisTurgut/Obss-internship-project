package obss.intern.veyis.config.security;

import lombok.RequiredArgsConstructor;
import obss.intern.veyis.manageMentorships.entity.Admin;
import obss.intern.veyis.manageMentorships.entity.Users;
import obss.intern.veyis.service.AdminService;
import obss.intern.veyis.service.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * People who logged in from Google may not be exist in ldif file. So this custom authenticator class authenticates those
 * person using database.
 */

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final AdminService adminService;


    /**
     * Custom authenticate method authenticates users by checking database records.
     */
    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        String username = auth.getName();
        String password = auth.getCredentials().toString();
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        Users user_from_db = userService.getUser(username);
        Admin admin_from_db = adminService.getAdmin(username);

        if (user_from_db != null && encoder.matches(password, user_from_db.getPassword()))
            return new UsernamePasswordAuthenticationToken(username, password, Arrays.asList(new SimpleGrantedAuthority("ROLE_USERS")));
        else if (admin_from_db != null && encoder.matches(password, admin_from_db.getPassword()))
            return new UsernamePasswordAuthenticationToken(username, password, Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMINS")));
        else
            throw new BadCredentialsException("External system authentication failed");
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}
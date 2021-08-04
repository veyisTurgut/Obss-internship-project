package obss.intern.veyis.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/adminLogin").permitAll()
                .antMatchers("/userLogin").permitAll()
                .antMatchers("/activeSessions").permitAll()
                .anyRequest().fullyAuthenticated()
                //.and().formLogin()
                .and().httpBasic()
                //.and().oauth2Login()
                //.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        auth
                .ldapAuthentication()
                .userDnPatterns("uid={0},ou=people")
                .groupSearchBase("ou=groups")
                .contextSource()
                .url("ldap://localhost:8389/dc=springframework,dc=org")
                .and()
                .passwordCompare()
                .passwordEncoder(encoder)
                .passwordAttribute("userPassword");
        /*
        System.out.println(encoder.encode("admin1234"));
        System.out.println(encoder.encode("admin2345"));
        System.out.println(encoder.encode("user1234"));
        System.out.println(encoder.encode("user2345"));
        System.out.println(encoder.encode("user3456"));
        System.out.println(encoder.encode("user4567"));
        System.out.println(encoder.encode("user5678"));
        System.out.println(encoder.encode("user6789"));
        */
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}
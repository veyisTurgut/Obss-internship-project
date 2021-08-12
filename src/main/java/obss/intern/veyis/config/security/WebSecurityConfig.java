package obss.intern.veyis.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .exceptionHandling()
                // .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .and()
                .authorizeRequests()
                .antMatchers("/user/authenticate", "/login", "/user/me", "/", "/login/google", "/callback/", "/auth/**", "/oauth2/**").permitAll()
                //.antMatchers("/user/add").hasRole(ADMIN.name())
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                //.failureHandler(authenticationFailureHandler())
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login")
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
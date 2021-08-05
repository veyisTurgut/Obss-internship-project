package obss.intern.veyis.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    /*
    @Configuration
    @Order(1)
    public static class App1ConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/aplications/**")
                    .authorizeRequests().anyRequest().hasRole("ADMIN")
                    .and().httpBasic().authenticationEntryPoint(authenticationEntryPoint());
        }

        @Bean
        public AuthenticationEntryPoint authenticationEntryPoint() {
            BasicAuthenticationEntryPoint entryPoint =
                    new BasicAuthenticationEntryPoint();
            entryPoint.setRealmName("admin realm");
            return entryPoint;
        }
    }

    @Configuration
    @Order(2)
    public static class App2ConfigurationAdapter extends WebSecurityConfigurerAdapter {

        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/user/**")
                    .authorizeRequests().anyRequest().hasRole("USER")
                    .and()
                    // formLogin configuration
                    //.and()
                    .exceptionHandling()
                    .defaultAuthenticationEntryPointFor(
                            loginUrlauthenticationEntryPointWithWarning(),
                            new AntPathRequestMatcher("/user/private/**"))
                    .defaultAuthenticationEntryPointFor(
                            loginUrlauthenticationEntryPoint(),
                            new AntPathRequestMatcher("/user/general/**"));
        }

        @Bean
        public AuthenticationEntryPoint loginUrlauthenticationEntryPoint() {
            return new LoginUrlAuthenticationEntryPoint("/userLogin");
        }

        @Bean
        public AuthenticationEntryPoint loginUrlauthenticationEntryPointWithWarning() {
            return new LoginUrlAuthenticationEntryPoint("/userLoginWithWarning");
        }
    }
*/
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/user/authenticate", "/login", "/user/me").permitAll()
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
                .and()
                .oauth2Login();
    }
    /*
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
*/

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
package obss.intern.veyis.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * This method is written in order to restrict endpoints to certain authority types.
 * <br/> //@PreAuthorize("hasAnyAuthority('ROLE_USERS','ROLE_ADMIN')")// and
 * <br/> //@PreAuthorize("hasAuthority('ROLE_USERS')")//
 * <br/> annotations are used in endpoints.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class MethodSecurityConfig
        extends GlobalMethodSecurityConfiguration {
}
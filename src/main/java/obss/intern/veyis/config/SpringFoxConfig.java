package obss.intern.veyis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * Config class for OpenAPI Specification.
 * url: http://localhost:8080/swagger-ui.html
 */
@Configuration
@EnableSwagger2
public class SpringFoxConfig {
    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("obss.intern"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo());
    }
    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "MENTORSHIP MANAGEMENT SYSTEM",
                null,
                "v1.0",
                null,
                new Contact("Adalet Veyis Turgut","https://www.linkedin.com/in/adalet-veyis-turgut-b64973190/","vturgut68@gmail.com"),
                null,
                null,
                Collections.emptyList()
        );
    }
}
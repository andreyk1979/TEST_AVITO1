package com.amr.project.webapp.config.swagger;

import com.amr.project.model.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.amr.project"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(outApiInfo())
                .ignoredParameterTypes(User.class);
    }

    private ApiInfo outApiInfo() {
        return new ApiInfo(
                "Project avito2_1",
                "This is project avito2_1 documentation",
                "0.0.1-SNAPSHOT",
                "/api/swagger",
                new Contact("Jon", "/api/swagger", "name@mail.com"),
                "",
                "",
                new ArrayList()
        );
    }
}

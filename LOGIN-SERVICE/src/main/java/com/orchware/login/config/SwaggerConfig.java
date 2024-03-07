package com.orchware.login.config;

//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.info.License;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.security.SecurityScheme;

import java.util.List;
import java.util.Map;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
//@EnableSwagger2
//@SecuritySchemes(
//@io.swagger.v3.oas.annotations.security.SecurityScheme(
//        name = "Bearer",
//        type = SecuritySchemeType.APIKEY,
//        bearerFormat = "JWT",
//        scheme = "bearer",
//        in = SecuritySchemeIn.HEADER
//)
//)

@OpenAPIDefinition(
//        info = @io.swagger.v3.oas.annotations.info.Info(title = "REST API", version = "1.1",
//        contact = @Contact(name = "Chinna", email = "java4chinna@gmail.com")),
        security = {
//        @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "basicAuth"),
                @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "Bearer Token")}
)
@SecuritySchemes({
//        @io.swagger.v3.oas.annotations.security.SecurityScheme(name = "basicAuth", type = SecuritySchemeType.HTTP, scheme = "bearer"),
        @io.swagger.v3.oas.annotations.security.SecurityScheme(name = "Bearer Token", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
})
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(
             @Value("${swagger.host}") String swaggerHost
//             @Value("${application-version}") String appVersion
    ) {

        Server server = new Server();
//        if (swaggerHost == null) {
//            server.setUrl("http://localhost:8080/auth");
//            server.setDescription("Localhost");
//        } else {
        server.setUrl(swaggerHost + "/auth");
        server.setDescription("host");
//        }
//                .license(new License().name("Apache 2.0").url("http://springdoc.org")));
        SecurityRequirement securityRequirement = new SecurityRequirement();
        securityRequirement.addList("name", "Authorization");
        securityRequirement.addList("type", SecurityScheme.Type.APIKEY.toString());
        securityRequirement.addList("bearerFormat", "JWT");
        securityRequirement.addList("scheme", "bearer");
        securityRequirement.addList("in", SecurityScheme.In.HEADER.toString());
        Components components = new Components();
        SecurityScheme securityScheme = new SecurityScheme();
        securityScheme.setType(SecurityScheme.Type.APIKEY);
        securityScheme.setName("Authorization");
//        securityScheme.setDescription("Bearer Authentication");
        securityScheme.setIn(SecurityScheme.In.HEADER);
        securityScheme.setScheme("bearer");
        securityScheme.setBearerFormat("JWT");
        components.setSecuritySchemes(Map.of("Authorization", securityScheme));
        return new OpenAPI()
//                .security(List.of(securityRequirement))
                .info(new Info()
                .title("Auth application API")
                .version("1.0")
                .description("Authentication/Authorization functionalities")
                .termsOfService("http://swagger.io/terms/")
                )
                .servers(List.of(server))
//                .components(components)
                ;
    }

    /*class OrchSecurityScheme extends SecurityScheme {

        @Override
        public SecuritySchemeType type() {
            return SecuritySchemeType.HTTP;
        }

        @Override
        public String name() {
            return "Bearer Authentication";
        }

        @Override
        public String description() {
            return "Bearer Authentication";
        }

        @Override
        public String paramName() {
            return null;
        }

        @Override
        public SecuritySchemeIn in() {
            return SecuritySchemeIn.HEADER;
        }

        @Override
        public String scheme() {
            return "bearer";
        }

        @Override
        public String bearerFormat() {
            return "JWT";
        }

        @Override
        public OAuthFlows flows() {
            return null;
        }

        @Override
        public String openIdConnectUrl() {
            return null;
        }

        @Override
        public Extension[] extensions() {
            return new Extension[0];
        }

        @Override
        public String ref() {
            return null;
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return null;
        }
    }*/

}

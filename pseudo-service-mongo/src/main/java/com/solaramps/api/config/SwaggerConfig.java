package com.solaramps.api.config;

//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.info.License;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@OpenAPIDefinition(security = {@io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "Bearer Token")})
@SecuritySchemes({@io.swagger.v3.oas.annotations.security.SecurityScheme(name = "Bearer Token", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")})
public class SwaggerConfig {

//    @Value("${app.behost}")
//    private String beHost;

    @Bean
    public OpenAPI customOpenAPI(
            @Value("${swagger.host}") String swaggerHost
//             @Value("${application-version}") String appVersion
    ) {

        Server server = new Server();
//        if (swaggerHost == null) {
//            server.setUrl("http://localhost:8080/core/core");
//            server.setDescription("Localhost");
//        } else {
        server.setUrl(swaggerHost + "/core/core");
        server.setDescription("host");
//        }
//        server.setUrl("https://sidevspring-gateway-service.azuremicroservices.io/core/core");
//                .license(new License().name("Apache 2.0").url("http://springdoc.org")));
        return new OpenAPI()
                .info(new Info()
                .title("Core application API")
                .version("1.0")
                .description("Core functionalities\n" +
                        "https://solarinformatics.atlassian.net/wiki/spaces/ORE/pages/627769345/Orchestration+Platform+High+Level+Functionalities")
                .termsOfService("http://swagger.io/terms/"))
                .servers(List.of(server));
    }

}

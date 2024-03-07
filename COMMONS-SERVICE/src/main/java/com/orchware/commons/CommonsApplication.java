package com.orchware.commons;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.core.env.AbstractEnvironment;

@SpringBootApplication
@EnableDiscoveryClient
/*@OpenAPIDefinition(
		servers = {
				@Server(url = "http://localhost:8080/commons/commons", description = "Default Server URL 2")
		},
		info = @Info(
				title = "Commons Swagger",
				version = "1.0.0",
				description = "Commons Swagger Description"
		)
)*/
public class CommonsApplication {

	static {
		if (System.getenv("PROFILE") != null) {
			System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, System.getenv("PROFILE"));
		} else {
			System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "local"); // TODO: add "local" in run configurations
		}
		/*if (System.getenv("PROFILE") != null) {
			System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, System.getenv("PROFILE"));
		}*/
	}

	public static void main(String[] args) {
		SpringApplication.run(CommonsApplication.class, args);
	}

}

package com.orchware.core;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.AbstractEnvironment;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
/*@OpenAPIDefinition(
		servers = {
				@Server(url = "http://localhost:8080/core/core", description = "Default Server URL")
		}
)*/
//@ImportAutoConfiguration({FeignAutoConfiguration.class})
//@OpenAPIDefinition(info = @Info(title = "Employees API", version = "3.0.0", description = "Employees Information"))
public class CentralCoreApplication {

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
		try{
			SpringApplication.run(CentralCoreApplication.class, args);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}

}

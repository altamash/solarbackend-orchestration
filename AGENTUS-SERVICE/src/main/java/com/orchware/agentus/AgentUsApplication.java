package com.orchware.agentus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AgentUsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgentUsApplication.class, args);
	}

}

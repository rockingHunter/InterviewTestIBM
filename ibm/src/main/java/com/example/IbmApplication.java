package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class IbmApplication {

	public static void main(String[] args) {
		SpringApplicationBuilder builder = configureApplication(new SpringApplicationBuilder());
		SpringApplication app =builder.build();
		app.run(args);
		//SpringApplication.run(IbmApplication.class, args);
	}
	
	static SpringApplicationBuilder configureApplication(SpringApplicationBuilder builder) {
		return builder.sources(IbmApplication.class);
	}

}

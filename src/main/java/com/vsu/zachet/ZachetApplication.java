package com.vsu.zachet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class ZachetApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZachetApplication.class, args);
	}

}

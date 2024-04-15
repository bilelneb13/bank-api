package com.finologee.apifinologeebank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class ApiFinologeeBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiFinologeeBankApplication.class, args);
	}

}

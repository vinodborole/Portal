package com.vinodborole.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import it.ozimov.springboot.mail.configuration.EnableEmailTools;

@SpringBootApplication
@EnableEmailTools
@EnableCaching
public class PortalPlatformApplication {
	public static void main(String[] args) {
		SpringApplication.run(PortalPlatformApplication.class, args);
	}

}

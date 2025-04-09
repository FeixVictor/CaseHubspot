package com.meetime.casehubspot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CasehubspotApplication {

	public static void main(String[] args) {
		SpringApplication.run(CasehubspotApplication.class, args);
	}

}

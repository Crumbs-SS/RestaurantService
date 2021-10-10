package com.crumbs.fss;

import com.crumbs.fss.security.SecretManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestaurantServiceApplication {
	public static void main(String[] args) {

		final String DB_ENDPOINT = SecretManager.getSecret("DB_ENDPOINT-FZfsSn");
		final String DB_USERNAME = SecretManager.getSecret("DB_USERNAME-pPctYs");
		final String DB_PASSWORD = SecretManager.getSecret("DB_PASSWORD-QE1V4j");
		System.setProperty("DB_ENDPOINT", DB_ENDPOINT);
		System.setProperty("DB_USERNAME", DB_USERNAME);
		System.setProperty("DB_PASSWORD", DB_PASSWORD);
		SpringApplication.run(RestaurantServiceApplication.class, args);
	}

}

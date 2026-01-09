package com.company.user_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(
		exclude = {
				UserDetailsServiceAutoConfiguration.class
		}
)
public class SportsVenueBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportsVenueBookingApplication.class, args);
	}

}

package io.gateways.userservice;

import io.gateways.userservice.entities.Role;
import io.gateways.userservice.entities.User;
import io.gateways.userservice.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class UserserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserserviceApplication.class, args);
	}

	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_MANAGER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));
			userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

			userService.saveUser(new User(null, "Joe Doe", "joe", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "John Doe", "john", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "Sam Samsung", "sam", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "Homero Simpson", "homero", "1234", new ArrayList<>()));

			userService.addUserToRole("joe", "ROLE_USER");
			userService.addUserToRole("john", "ROLE_MANAGER");
			userService.addUserToRole("sam", "ROLE_ADMIN");
			userService.addUserToRole("homero", "ROLE_SUPER_ADMIN");
			userService.addUserToRole("homero", "ROLE_ADMIN");
			userService.addUserToRole("homero", "ROLE_USER");
		};
	}
}
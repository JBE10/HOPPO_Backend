package com.example.HPPO_Backend;

import com.example.HPPO_Backend.entity.Role;
import com.example.HPPO_Backend.entity.User;
import com.example.HPPO_Backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class HppoBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(HppoBackendApplication.class, args);
	}


	@Bean
	public CommandLineRunner createInitialUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {

			if (userRepository.findByEmail("jespino@uade.edu.ar").isEmpty()) {
				User vendedor = User.builder()
						.name("Vendedor")
						.lastName("Test")
						.email("jespino@uade.edu.ar")
						.username("jespino@uade.edu.ar")
						.password(passwordEncoder.encode("pass123"))
						.role(Role.VENDEDOR)
						.build();
				userRepository.save(vendedor);

			}
		};
	}
}
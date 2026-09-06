package com.example.demo;

import com.example.demo.run.Run;
import com.example.demo.run.RunRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class DemoApplication {
	private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(UserRestClient userRestClient) {
		return args -> {
			List<User> users = userRestClient.findAll();
			User u =  userRestClient.findById(3);
			System.out.println(u.toString());
		};
	}
}

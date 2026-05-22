package com.flarecon.mocktail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MocktailApplication {

	public static void main(String[] args) {
		SpringApplication.run(MocktailApplication.class, args);
		System.out.println(Constants.SUCCESS_MESSAGE);
	}

}

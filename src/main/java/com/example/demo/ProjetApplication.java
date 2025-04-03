package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjetApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ProjetApplication.class, args);
	}
	public void run(String ... args) {
		System.out.println("Hello I2");
	}

}

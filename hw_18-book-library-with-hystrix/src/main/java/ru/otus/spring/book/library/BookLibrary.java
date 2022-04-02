package ru.otus.spring.book.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@EnableCircuitBreaker
public class BookLibrary {

	public static void main(String[] args) {
		SpringApplication.run(BookLibrary.class, args);
	}

}

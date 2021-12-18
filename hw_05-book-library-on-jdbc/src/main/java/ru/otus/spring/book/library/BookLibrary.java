package ru.otus.spring.book.library;

//import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookLibrary {

	public static void main(String[] args) {
		SpringApplication.run(BookLibrary.class, args);

		//Console.main(args);
	}

}

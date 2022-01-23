package ru.otus.spring.book.library;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.spring.book.library.service.JobService;

@EnableMongock
@SpringBootApplication
public class BookLibrary {

	public static void main(String[] args) {
		SpringApplication.run(BookLibrary.class, args)
				.getBean(JobService.class)
				.runMigration();
	}

}

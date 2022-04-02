package ru.otus.spring.integration.dish.competition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.spring.integration.dish.competition.service.CompetitionService;

@SpringBootApplication
public class DishCompetition {

	public static void main(String[] args) {
		SpringApplication.run(DishCompetition.class, args)
				.getBean(CompetitionService.class)
				.run();
	}

}

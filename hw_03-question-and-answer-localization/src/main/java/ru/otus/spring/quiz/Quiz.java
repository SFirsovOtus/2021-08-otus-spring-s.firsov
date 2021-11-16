package ru.otus.spring.quiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.spring.quiz.service.QuizService;

@SpringBootApplication
public class Quiz {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Quiz.class, args);

		QuizService quizService = context.getBean(QuizService.class);

		quizService.conductQuiz();
	}

}

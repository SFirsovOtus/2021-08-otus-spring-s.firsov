package ru.otus.spring.quiz;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring.quiz.service.QuizService;

@ComponentScan
@PropertySource("classpath:application.properties")
public class Quiz {

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Quiz.class);

        QuizService quizService = context.getBean(QuizService.class);

        quizService.conductQuiz();
    }

}

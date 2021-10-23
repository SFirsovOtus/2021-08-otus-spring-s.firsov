package ru.otus.spring.quiz;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring.quiz.service.QuestionService;

@ComponentScan
@PropertySource("classpath:application.properties")
public class Quiz {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Quiz.class);

        QuestionService questionService = context.getBean(QuestionService.class);
        questionService.conductQuiz();
    }

}

package ru.otus.spring.quiz;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.quiz.service.QuestionService;

public class Quiz {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");

        QuestionService questionService = context.getBean(QuestionService.class);
        Integer questionCount = questionService.printAll();
        if (questionCount == 0) {
            System.out.println("The sailors have no questions!");
        }
    }

}

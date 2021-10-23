package ru.otus.spring.quiz.service;

import ru.otus.spring.quiz.domain.Question;
import ru.otus.spring.quiz.domain.Student;

import java.util.List;

public interface QuestionService {

    List<Question> getAll();

    Integer printSingle(Question question);

    void printBulk(List<Question> questions);

    Integer printAll();

    Student greet();

    void conductQuiz();

}
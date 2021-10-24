package ru.otus.spring.quiz.service;

import ru.otus.spring.quiz.domain.Question;

import java.util.List;

public interface QuestionService {

    List<Question> getAll();

    void printSingle(Question question);

    void printBulk(List<Question> questions);

    Integer printAll();

}
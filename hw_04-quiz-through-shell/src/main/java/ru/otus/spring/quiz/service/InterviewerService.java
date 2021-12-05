package ru.otus.spring.quiz.service;

import ru.otus.spring.quiz.domain.Question;

public interface InterviewerService {

    void askQuestion(int questionNumber, Question question);

    String acceptAnswer();

}

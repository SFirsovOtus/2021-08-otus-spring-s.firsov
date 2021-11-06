package ru.otus.spring.quiz.service;

import ru.otus.spring.quiz.exception.QuestionsNotFoundException;
import ru.otus.spring.quiz.exception.QuestionsReadingException;

public interface QuizService {

    void conductQuiz() throws QuestionsReadingException, QuestionsNotFoundException;

}

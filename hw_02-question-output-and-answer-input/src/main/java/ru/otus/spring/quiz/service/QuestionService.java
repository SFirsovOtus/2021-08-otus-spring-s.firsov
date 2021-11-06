package ru.otus.spring.quiz.service;

import ru.otus.spring.quiz.exception.QuestionsReadingException;
import ru.otus.spring.quiz.domain.Question;

import java.util.List;

public interface QuestionService {

    List<Question> getAllQuestions() throws QuestionsReadingException;

    void askQuestion(int questionNumber, Question question);

    String acceptAnswer();

    boolean checkRightnessOfAnswerToQuestion(String acceptedAnswer, Question askedQuestion);

}

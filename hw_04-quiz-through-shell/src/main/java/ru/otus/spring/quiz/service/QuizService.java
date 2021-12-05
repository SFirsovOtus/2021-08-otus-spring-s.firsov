package ru.otus.spring.quiz.service;

import ru.otus.spring.quiz.domain.Student;

public interface QuizService {

    void conductQuiz(Student student);

    void greet(Student student);

    String reportThatNeedToIntroduceYourself();

    String reportThatAlreadyIntroducedYourself(Student student);

}

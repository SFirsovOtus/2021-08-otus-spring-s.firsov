package ru.otus.spring.quiz.exception;

public class QuestionsNotFoundException extends Exception {

    public QuestionsNotFoundException() {
        super("Questions not found in storage");
    }

}

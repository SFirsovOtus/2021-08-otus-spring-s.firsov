package ru.otus.spring.quiz.exception;

import lombok.Getter;

import java.io.IOException;

@Getter
public class QuestionsReadingException extends IOException {

    private final String resourcePath;

    public QuestionsReadingException(String resourcePath, Throwable cause) {
        super(String.format("Can't read resource %s", resourcePath), cause);
        this.resourcePath = resourcePath;
    }

}

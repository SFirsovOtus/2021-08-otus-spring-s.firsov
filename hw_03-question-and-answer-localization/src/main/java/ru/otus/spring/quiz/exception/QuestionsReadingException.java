package ru.otus.spring.quiz.exception;

import lombok.Getter;

import java.io.IOException;

@Getter
public class QuestionsReadingException extends IOException {

    private final String resourcePath;
    private final String resourcePathDefault;

    public QuestionsReadingException(String resourcePath, String resourcePathDefault, Throwable cause) {
        super(String.format("Can't read resources %s and %s", resourcePath, resourcePathDefault), cause);
        this.resourcePath = resourcePath;
        this.resourcePathDefault = resourcePathDefault;
    }

}

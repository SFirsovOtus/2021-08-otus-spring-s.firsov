package ru.otus.spring.quiz.service;

public interface LocalizationIOService {

    void print(Object object);

    String scan();

    String getMessage(String property, String ... args);

    void printPropertyValue(String property, String ... args);

}

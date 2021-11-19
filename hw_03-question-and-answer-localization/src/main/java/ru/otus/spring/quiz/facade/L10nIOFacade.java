package ru.otus.spring.quiz.facade;

public interface L10nIOFacade {

    void print(Object object);

    String scan();

    String getMessage(String property, String ... args);

    void printPropertyValue(String property, String ... args);

}

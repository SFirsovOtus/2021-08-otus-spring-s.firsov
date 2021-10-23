package ru.otus.spring.quiz.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Question {

    private final String formulation;
    private final Answer answer;

}

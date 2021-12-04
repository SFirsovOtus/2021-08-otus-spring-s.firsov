package ru.otus.spring.quiz.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Answer {

    private final List<Variant> variants;

    @Getter
    @AllArgsConstructor
    public static class Variant {

        private final String formulation;
        private final boolean right;

    }

}

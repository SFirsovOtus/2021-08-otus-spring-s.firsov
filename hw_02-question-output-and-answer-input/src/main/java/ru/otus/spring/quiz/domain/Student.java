package ru.otus.spring.quiz.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Student {

    private final String name;
    private final String surname;

    @Override
    public String toString() {
        return String.format("%s %s", this.name, this.surname);
    }

}

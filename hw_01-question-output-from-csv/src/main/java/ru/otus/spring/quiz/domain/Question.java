package ru.otus.spring.quiz.domain;

public class Question {

    private final String formulation;
    private final Answer answer;

    public Question(String formulation, Answer answer) {
        this.formulation = formulation;
        this.answer = answer;
    }

    public String getFormulation() {
        return formulation;
    }

    public Answer getAnswer() {
        return answer;
    }

}

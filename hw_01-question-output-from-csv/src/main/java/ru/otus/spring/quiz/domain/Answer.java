package ru.otus.spring.quiz.domain;

import java.util.List;

public class Answer {

    private final List<Variant> variants;
    private String resultFormulation;

    public Answer(List<Variant> variants) {
        this.variants = variants;
    }

    public List<Variant> getVariants() {
        return variants;
    }

    public String getResultFormulation() {
        return resultFormulation;
    }


    public static class Variant {

        private final String formulation;
        private final Boolean isRight;

        public Variant(String formulation, Boolean isRight) {
            this.formulation = formulation;
            this.isRight = isRight;
        }

        public String getFormulation() {
            return formulation;
        }

        public Boolean getIsRight() {
            return isRight;
        }

    }

}

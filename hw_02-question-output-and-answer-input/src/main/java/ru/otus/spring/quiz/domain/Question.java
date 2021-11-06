package ru.otus.spring.quiz.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Question {

    private final String formulation;
    private final Answer answer;

    public int getRightAnswerVariantNumber() {
        List<Answer.Variant> answerVariants = this.answer.getVariants();
        int rightAnswerVariantNumber = 0;

        for(int i = 0; i < answerVariants.size(); i++) {
            if (answerVariants.get(i).isRight()) {
                rightAnswerVariantNumber = i + 1;  // "+ 1", т. к. нумерация вариантов ответа начинается с 1 в отличие от индексов списка, которые начинаются с 0
                break;
            }
        }

        return rightAnswerVariantNumber;
    }

    @Override
    public String toString() {
        StringBuilder question = new StringBuilder(this.formulation);
        List<Answer.Variant> answerVariants = this.answer.getVariants();

        for (int i = 0; i < answerVariants.size(); i++) {
            question.append(System.lineSeparator())
                    .append("    ")
                    .append(i + 1)
                    .append(". ")
                    .append(answerVariants.get(i).getFormulation());
        }

        return question.toString();
    }

    public String toStringWithQuestionNumber(int questionNumber) {
        return String.format("%s. %s", questionNumber, this);
    }

}

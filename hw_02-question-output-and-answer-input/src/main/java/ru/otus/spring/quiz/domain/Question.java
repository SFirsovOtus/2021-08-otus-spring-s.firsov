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

}

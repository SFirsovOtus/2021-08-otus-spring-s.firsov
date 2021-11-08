package ru.otus.spring.quiz.mapper;

import org.springframework.stereotype.Service;
import ru.otus.spring.quiz.domain.Answer;
import ru.otus.spring.quiz.domain.Question;

import java.util.List;

@Service
public class QuestionMapper {

    public String mapToStringWithAnswerVariants(Question question) {
        StringBuilder sbQuestion = new StringBuilder(question.getFormulation());
        List<Answer.Variant> answerVariants = question.getAnswer().getVariants();

        for (int i = 0; i < answerVariants.size(); i++) {
            sbQuestion
                    .append(System.lineSeparator())
                    .append("    ")
                    .append(i + 1)
                    .append(". ")
                    .append(answerVariants.get(i).getFormulation());
        }

        return sbQuestion.toString();
    }

    public String mapToStringWithQuestionNumber(int questionNumber, Question question) {
        return String.format("%s. %s", questionNumber, mapToStringWithAnswerVariants(question));
    }

}

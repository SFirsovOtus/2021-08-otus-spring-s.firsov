package ru.otus.spring.quiz.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.quiz.dao.QuestionDao;
import ru.otus.spring.quiz.domain.Question;
import ru.otus.spring.quiz.exception.QuestionsReadingException;
import ru.otus.spring.quiz.mapper.L10nMapper;

import java.util.List;

@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao questionDao;
    private final L10nMapper l10nMapper;

    @Override
    public List<Question> getAllQuestions() throws QuestionsReadingException {
        return l10nMapper.localizeQuestions(questionDao.readAll());
    }

    @Override
    public boolean checkRightnessOfAnswerToQuestion(String acceptedAnswer, Question askedQuestion) {
        int rightAnswerVariantNumber = askedQuestion.getRightAnswerVariantNumber();

        // будем считать, что на вопрос ответили правильно, если
        // вопрос без вариантов и что-нибудь ввели в качестве ответа,
        // или выбранный вариант ответа совпал с правильным вариантом, когда варианты ответов есть
        return (rightAnswerVariantNumber == 0 && !acceptedAnswer.trim().isEmpty()) ||
                Integer.toString(rightAnswerVariantNumber).equals(acceptedAnswer.trim());
    }

}

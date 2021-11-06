package ru.otus.spring.quiz.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.quiz.dao.QuestionDao;
import ru.otus.spring.quiz.exception.QuestionsReadingException;
import ru.otus.spring.quiz.domain.Question;

import java.util.List;

@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao questionDao;
    private final ConsoleService consoleService;


    @Override
    public List<Question> getAllQuestions() throws QuestionsReadingException {
        return questionDao.readAll();
    }

    @Override
    public void askQuestion(int questionNumber, Question question) {
        consoleService.print(question.toStringWithQuestionNumber(questionNumber));
    }

    @Override
    public String acceptAnswer() {
        return consoleService.scan();
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

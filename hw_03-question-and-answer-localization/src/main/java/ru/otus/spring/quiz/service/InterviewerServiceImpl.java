package ru.otus.spring.quiz.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.quiz.domain.Question;
import ru.otus.spring.quiz.facade.L10nIOFacade;
import ru.otus.spring.quiz.mapper.QuestionMapper;

@Service
@AllArgsConstructor
public class InterviewerServiceImpl implements InterviewerService {

    private final L10nIOFacade l10nIOFacade;
    private final QuestionMapper questionMapper;


    @Override
    public void askQuestion(int questionNumber, Question question) {
        l10nIOFacade.print(questionMapper.mapToStringWithQuestionNumber(questionNumber, question));
    }

    @Override
    public String acceptAnswer() {
        return l10nIOFacade.scan();
    }

}

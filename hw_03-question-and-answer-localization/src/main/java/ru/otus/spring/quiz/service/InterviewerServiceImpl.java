package ru.otus.spring.quiz.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.quiz.domain.Question;
import ru.otus.spring.quiz.mapper.QuestionMapper;

@Service
@AllArgsConstructor
public class InterviewerServiceImpl implements InterviewerService {

    private final IOService ioService;
    private final QuestionMapper questionMapper;


    @Override
    public void askQuestion(int questionNumber, Question question) {
        ioService.print(questionMapper.mapToStringWithQuestionNumber(questionNumber, question));
    }

    @Override
    public String acceptAnswer() {
        return ioService.scan();
    }

}

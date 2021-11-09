package ru.otus.spring.quiz.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.quiz.domain.Answer;
import ru.otus.spring.quiz.domain.Question;
import ru.otus.spring.quiz.mapper.QuestionMapper;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InterviewerServiceImplTest {

    @Mock
    private IOService ioService;
    @Mock
    private QuestionMapper questionMapper;

    private InterviewerService interviewerService;

    @BeforeEach
    void setUp() {
        interviewerService = new InterviewerServiceImpl(ioService, questionMapper);
    }


    @Test
    void askQuestionShouldExecutePrintOfQuestionWithNumber() {
        int questionNumber = 123;
        Question question = new Question("Any question", new Answer(Collections.emptyList()));
        String someText = "Some text";

        doReturn(someText).when(questionMapper).mapToStringWithQuestionNumber(questionNumber, question);

        interviewerService.askQuestion(questionNumber, question);

        verify(questionMapper, times(1)).mapToStringWithQuestionNumber(questionNumber, question);
        verify(ioService, times(1)).print(someText);
    }

    @Test
    void acceptAnswerShouldReturnEnteredAnswer() {
        String expectedAnswer = "Any answer";

        doReturn(expectedAnswer).when(ioService).scan();

        String actualAnswer = interviewerService.acceptAnswer();

        verify(ioService, times(1)).scan();
        assertEquals(expectedAnswer, actualAnswer);
    }

}

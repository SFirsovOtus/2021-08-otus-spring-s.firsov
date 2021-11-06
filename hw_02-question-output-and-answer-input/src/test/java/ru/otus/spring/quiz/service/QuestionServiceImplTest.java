package ru.otus.spring.quiz.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.quiz.dao.QuestionDao;
import ru.otus.spring.quiz.domain.Answer;
import ru.otus.spring.quiz.domain.Question;
import ru.otus.spring.quiz.exception.QuestionsReadingException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {

    @Mock
    private QuestionDao questionDao;
    @Mock
    private ConsoleService consoleService;

    private QuestionService questionService;

    @BeforeEach
    void setUp() {
        questionService = new QuestionServiceImpl(questionDao, consoleService);
    }


    @Test
    void getAllQuestionsShouldExecuteReadAllOfQuestionDao() throws QuestionsReadingException {
        questionService.getAllQuestions();
        verify(questionDao, times(1)).readAll();
    }

    @Test
    void askQuestionShouldExecutePrintOfQuestionWithNumber() {
        int questionNumber = 123;
        Question question = new Question("Any question", new Answer(Collections.emptyList()));
        String textForPrinting = question.toStringWithQuestionNumber(questionNumber);

        doNothing().when(consoleService).print(anyString());

        questionService.askQuestion(questionNumber, question);

        verify(consoleService, times(1)).print(textForPrinting);
    }

    @Test
    void acceptAnswerShouldReturnEnteredAnswer() {
        String expectedAnswer = "Any answer";

        doReturn(expectedAnswer).when(consoleService).scan();

        String actualAnswer = questionService.acceptAnswer();

        verify(consoleService, times(1)).scan();
        assertEquals(expectedAnswer, actualAnswer);
    }

    @Test
    void checkRightnessOfAnswerToQuestionWhenChosenRightVariant() {
        List<Answer.Variant> variants = List.of(
                new Answer.Variant("This variant is bad", false),
                new Answer.Variant("This variant is good", true)
        );
        Question askedQuestion = new Question("Strange question", new Answer(variants));
        String acceptedAnswer = "  2 ";

        boolean right = questionService.checkRightnessOfAnswerToQuestion(acceptedAnswer, askedQuestion);

        assertTrue(right);
    }

    @Test
    void checkRightnessOfAnswerToQuestionWhenChosenNotRightVariant() {
        List<Answer.Variant> variants = List.of(
                new Answer.Variant("This variant is bad", false),
                new Answer.Variant("This variant is good", true)
        );
        Question askedQuestion = new Question("Strange question", new Answer(variants));
        String acceptedAnswer = "1";

        boolean right = questionService.checkRightnessOfAnswerToQuestion(acceptedAnswer, askedQuestion);

        assertFalse(right);
    }

    @Test
    void checkRightnessOfAnswerToQuestionWhenNoVariantsAndEnteredAnswer() {
        Question askedQuestion = new Question("Strange question", new Answer(Collections.emptyList()));
        String acceptedAnswer = "Strange answer";

        boolean right = questionService.checkRightnessOfAnswerToQuestion(acceptedAnswer, askedQuestion);

        assertTrue(right);
    }

    @Test
    void checkRightnessOfAnswerToQuestionWhenNoVariantsAndNoAnswer() {
        Question askedQuestion = new Question("Strange question", new Answer(Collections.emptyList()));
        String acceptedAnswer = "  ";

        boolean right = questionService.checkRightnessOfAnswerToQuestion(acceptedAnswer, askedQuestion);

        assertFalse(right);
    }

}

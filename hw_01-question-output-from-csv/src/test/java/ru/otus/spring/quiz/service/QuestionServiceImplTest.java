package ru.otus.spring.quiz.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.quiz.dao.QuestionDao;
import ru.otus.spring.quiz.domain.Answer;
import ru.otus.spring.quiz.domain.Question;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {

    @Mock
    private QuestionDao questionDao;

    @InjectMocks
    @Spy
    private QuestionServiceImpl questionServiceImpl;

    private QuestionService questionService;

    @BeforeEach
    void setUp() {
        questionService = new QuestionServiceImpl(questionDao);
    }

    @Test
    void getAllShouldExecuteReadAllOfQuestionDao() {
        questionService.getAll();
        verify(questionDao, times(1)).readAll();
    }

    @Test
    void printSingleShouldAddNumbersForVariants() {
        List<Answer.Variant> variants = List.of(
                new Answer.Variant("This variant is bad", Boolean.FALSE),
                new Answer.Variant("This variant is good", Boolean.TRUE)
        );
        Question question = new Question("Strange question", new Answer(variants));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        questionService.printSingle(question);

        String[] outputStrings = outputStream.toString()
                .split("\r\n");

        assertEquals("    1. " + variants.get(0).getFormulation(), outputStrings[1]);
        assertEquals("    2. " + variants.get(1).getFormulation(), outputStrings[2]);
    }

    @Test
    void printBulkShouldAddNumbersForQuestions() {
        List<Question> questions = List.of(
                new Question("Hard question", new Answer(Collections.emptyList())),
                new Question("Simple question", new Answer(Collections.emptyList()))
        );

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        questionService.printBulk(questions);

        String[] outputStrings = outputStream.toString()
                .split("\r\n\r\n");

        assertEquals("1. " + questions.get(0).getFormulation(), outputStrings[0]);
        assertEquals("2. " + questions.get(1).getFormulation(), outputStrings[1]);
    }

    @Test
    void printAllShouldUseOtherMethodsOfTheSameService() {
        List<Question> questions = List.of(
                new Question("Hard question", new Answer(Collections.emptyList())),
                new Question("Medium question", new Answer(Collections.emptyList())),
                new Question("Simple question", new Answer(Collections.emptyList()))
        );

        doReturn(questions).when(questionServiceImpl).getAll();
        doNothing().when(questionServiceImpl).printBulk(anyList());

        Integer questionCount = questionServiceImpl.printAll();

        verify(questionServiceImpl, times(1)).getAll();
        verify(questionServiceImpl, times(1)).printBulk(questions);
        assertEquals(questions.size(), questionCount);
    }

}

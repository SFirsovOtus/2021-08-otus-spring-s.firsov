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
import ru.otus.spring.quiz.domain.Student;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
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

        Integer rightVariantNumber = questionService.printSingle(question);

        String[] outputStrings = outputStream.toString()
                .split(System.lineSeparator());

        assertEquals("    1. " + variants.get(0).getFormulation(), outputStrings[1]);
        assertEquals("    2. " + variants.get(1).getFormulation(), outputStrings[2]);
        assertEquals(2, rightVariantNumber);
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
                .split(System.lineSeparator() + System.lineSeparator());

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
        doNothing().when(questionServiceImpl).printBulk(questions);

        Integer questionCount = questionServiceImpl.printAll();

        verify(questionServiceImpl, times(1)).getAll();
        verify(questionServiceImpl, times(1)).printBulk(questions);
        assertEquals(questions.size(), questionCount);
    }

    @Test
    void greetShouldReturnStudentWhoWasEntered() {
        Student expectedStudent = new Student("Imya", "Familiya");

        ByteArrayInputStream inputStream = new ByteArrayInputStream((expectedStudent.getName() + System.lineSeparator() + expectedStudent.getSurname()).getBytes());
        System.setIn(inputStream);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Student actualStudent = questionService.greet();

        List<String> outputStrings = Arrays.asList(outputStream.toString()
                .split(System.lineSeparator()));

        assertEquals("Hello, " + expectedStudent.getName() + " " + expectedStudent.getSurname() + "!", outputStrings.get(outputStrings.size() - 1));
        assertEquals(expectedStudent.getName(), actualStudent.getName());
        assertEquals(expectedStudent.getSurname(), actualStudent.getSurname());
    }

    @Test
    void conductQuizShouldFinishWhenNoQuestions() {
        doReturn(new Student("Imya", "Familiya")).when(questionServiceImpl).greet();
        doReturn(Collections.emptyList()).when(questionServiceImpl).getAll();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        questionServiceImpl.conductQuiz();

        String[] outputStrings = outputStream.toString()
                .split(System.lineSeparator());

        assertEquals("The sailors have no questions!", outputStrings[0]);
        assertEquals(1, outputStrings.length);
    }

    @Test
    void conductQuizShouldPrintGradeWhenExistQuestions() {
        List<Answer.Variant> variants = List.of(
                new Answer.Variant("This variant is bad", Boolean.FALSE),
                new Answer.Variant("This variant is good", Boolean.TRUE)
        );
        List<Question> questions = List.of(
                new Question("Hard question", new Answer(Collections.emptyList())),
                new Question("Medium question", new Answer(variants)),
                new Question("Simple question", new Answer(Collections.emptyList()))
        );
        Student student = new Student("Imya", "Familiya");

        doReturn(student).when(questionServiceImpl).greet();
        doReturn(questions).when(questionServiceImpl).getAll();
        doReturn(0, 2, 0).when(questionServiceImpl).printSingle(any(Question.class));

        ByteArrayInputStream inputStream = new ByteArrayInputStream(("Hard answer" + System.lineSeparator() + "2" + System.lineSeparator() + "" + System.lineSeparator()).getBytes());
        System.setIn(inputStream);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        questionServiceImpl.conductQuiz();

        List<String> outputStrings = Arrays.asList(outputStream.toString()
                .split(System.lineSeparator()));

        assertEquals("Let's begin quiz...", outputStrings.get(0));
        assertEquals(student.getName() + " " + student.getSurname() + ", your grade is 2", outputStrings.get(outputStrings.size() - 1));
    }

}

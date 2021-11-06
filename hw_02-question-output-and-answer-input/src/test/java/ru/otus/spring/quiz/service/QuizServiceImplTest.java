package ru.otus.spring.quiz.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.quiz.domain.Answer;
import ru.otus.spring.quiz.domain.Question;
import ru.otus.spring.quiz.domain.Student;
import ru.otus.spring.quiz.exception.QuestionsNotFoundException;
import ru.otus.spring.quiz.exception.QuestionsReadingException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuizServiceImplTest {

    @Mock
    private StudentService studentService;
    @Mock
    private QuestionService questionService;
    @Mock
    private ConsoleService consoleService;

    private QuizService quizService;

    @BeforeEach
    void setUp() {
        quizService = new QuizServiceImpl(studentService, questionService, consoleService);
    }


    @Test
    void conductQuizShouldHappenExceptionWhenNotReadQuestions() throws QuestionsReadingException {
        Student student = new Student("Jane", "Doe");

        when(studentService.askNameAndSurname()).thenReturn(student);
        when(questionService.getAllQuestions()).thenThrow(QuestionsReadingException.class);

        assertThrows(QuestionsReadingException.class, () -> quizService.conductQuiz());

        verify(studentService, times(1)).askNameAndSurname();
        verify(questionService, times(1)).getAllQuestions();
        verify(consoleService, times(1)).print(anyString());
    }

    @Test
    void conductQuizShouldHappenExceptionWhenNoQuestions() throws QuestionsReadingException {
        Student student = new Student("Jane", "Doe");
        String helloMessage = System.lineSeparator() + "Hello, " + student + "!";
        String noQuestionsMessage = "The sailors have no questions!" + System.lineSeparator();

        when(studentService.askNameAndSurname()).thenReturn(student);
        when(questionService.getAllQuestions()).thenReturn(Collections.emptyList());

        assertThrows(QuestionsNotFoundException.class, () -> quizService.conductQuiz());

        verify(studentService, times(1)).askNameAndSurname();
        verify(questionService, times(1)).getAllQuestions();
        verify(consoleService, times(1)).print(helloMessage);
        verify(consoleService, times(1)).print(noQuestionsMessage);
    }

    @Test
    void conductQuizShouldHappenExceptionWhenFoundQuestions() throws QuestionsReadingException, QuestionsNotFoundException {
        Student student = new Student("Imya", "Familiya");
        List<Answer.Variant> variants = List.of(
                new Answer.Variant("This variant is bad", false),
                new Answer.Variant("This variant is good", true),
                new Answer.Variant("This variant is strange", false)
        );
        List<Question> questions = List.of(
                new Question("Hard question", new Answer(Collections.emptyList())),
                new Question("Medium question", new Answer(variants)),
                new Question("Simple question", new Answer(Collections.emptyList())),
                new Question("Extra question", new Answer(variants))
        );
        String helloMessage = System.lineSeparator() + "Hello, " + student + "!";
        String beginQuizMessage = "Let's begin quiz..." + System.lineSeparator();
        String acceptedAnswerMessage = "Answer accepted." + System.lineSeparator() + System.lineSeparator();
        String resultMessage = student + ", your grade is " + 2 + ".";

        when(studentService.askNameAndSurname()).thenReturn(student);
        when(questionService.getAllQuestions()).thenReturn(questions);
        doReturn(" ", "2", "Right answer", "3").when(questionService).acceptAnswer();
        when(questionService.checkRightnessOfAnswerToQuestion(anyString(), any(Question.class))).thenReturn(false, true, true, false);

        quizService.conductQuiz();

        verify(studentService, times(1)).askNameAndSurname();
        verify(questionService, times(1)).getAllQuestions();
        verify(consoleService, times(1)).print(helloMessage);
        verify(consoleService, times(1)).print(beginQuizMessage);
        verify(questionService, times(questions.size())).askQuestion(anyInt(), any(Question.class));
        verify(questionService, times(questions.size())).acceptAnswer();
        verify(consoleService, times(questions.size())).print(acceptedAnswerMessage);
        verify(questionService, times(questions.size())).checkRightnessOfAnswerToQuestion(anyString(), any(Question.class));
        verify(consoleService, times(1)).print(resultMessage);
    }

}

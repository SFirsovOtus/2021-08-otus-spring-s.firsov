package ru.otus.spring.quiz.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.quiz.domain.Answer;
import ru.otus.spring.quiz.domain.Question;
import ru.otus.spring.quiz.domain.Student;
import ru.otus.spring.quiz.exception.QuestionsReadingException;
import ru.otus.spring.quiz.mapper.StudentMapper;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuizServiceImplTest {

    @Mock
    private StudentService studentService;
    @Mock
    private QuestionService questionService;
    @Mock
    private IOService ioService;
    @Mock
    private InterviewerService interviewerService;
    @Mock
    private StudentMapper studentMapper;

    private QuizService quizService;

    @BeforeEach
    void setUp() {
        quizService = new QuizServiceImpl(
                studentService, questionService, ioService, interviewerService, studentMapper);
    }


    @Test
    void conductQuizShouldHappenExceptionWhenNotReadQuestions() throws QuestionsReadingException {
        Student student = new Student("Jane", "Doe");
        String studentNameSurname = student.getName() + " " + student.getSurname();
        String postponementMessage = System.lineSeparator() +
                studentNameSurname + ", you're in luck!" + System.lineSeparator() +
                "The quiz will have to be postponed." + System.lineSeparator();

        when(studentService.askNameAndSurname()).thenReturn(student);
        when(questionService.getAllQuestions()).thenThrow(QuestionsReadingException.class);
        when(studentMapper.mapToStringWithNameSurnameOrder(student)).thenReturn(studentNameSurname);

        quizService.conductQuiz();

        verify(studentService, times(1)).askNameAndSurname();
        verify(questionService, times(1)).getAllQuestions();
        verify(studentMapper, times(1)).mapToStringWithNameSurnameOrder(student);
        verify(ioService, times(1)).print(postponementMessage);
    }

    @Test
    void conductQuizShouldHappenExceptionWhenNoQuestions() throws QuestionsReadingException {
        Student student = new Student("Jane", "Doe");
        String studentNameSurname = student.getName() + " " + student.getSurname();
        String helloMessage = System.lineSeparator() + "Hello, " + studentNameSurname + "!";
        String noQuestionsMessage = "The sailors have no questions!" + System.lineSeparator();

        when(studentService.askNameAndSurname()).thenReturn(student);
        when(studentMapper.mapToStringWithNameSurnameOrder(student)).thenReturn(studentNameSurname);
        when(questionService.getAllQuestions()).thenReturn(Collections.emptyList());

        quizService.conductQuiz();

        verify(studentService, times(1)).askNameAndSurname();
        verify(questionService, times(1)).getAllQuestions();
        verify(studentMapper, times(1)).mapToStringWithNameSurnameOrder(student);
        verify(ioService, times(1)).print(helloMessage);
        verify(ioService, times(1)).print(noQuestionsMessage);
    }

    @Test
    void conductQuizShouldCalculateGradeWhenFoundQuestions() throws QuestionsReadingException {
        Student student = new Student("Imya", "Familiya");
        String studentNameSurname = student.getName() + " " + student.getSurname();
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
        String helloMessage = System.lineSeparator() + "Hello, " + studentNameSurname + "!";
        String beginQuizMessage = "Let's begin quiz..." + System.lineSeparator();
        String acceptedAnswerMessage = "Answer accepted." + System.lineSeparator() + System.lineSeparator();
        String resultMessage = studentNameSurname + ", your grade is " + 2 + ".";

        when(studentService.askNameAndSurname()).thenReturn(student);
        when(questionService.getAllQuestions()).thenReturn(questions);
        when(studentMapper.mapToStringWithNameSurnameOrder(student)).thenReturn(studentNameSurname);
        doReturn(" ", "2", "Right answer", "3").when(interviewerService).acceptAnswer();
        when(questionService.checkRightnessOfAnswerToQuestion(anyString(), any(Question.class))).thenReturn(false, true, true, false);

        quizService.conductQuiz();

        verify(studentService, times(1)).askNameAndSurname();
        verify(questionService, times(1)).getAllQuestions();
        verify(ioService, times(1)).print(helloMessage);
        verify(ioService, times(1)).print(beginQuizMessage);
        verify(interviewerService, times(questions.size())).askQuestion(anyInt(), any(Question.class));
        verify(interviewerService, times(questions.size())).acceptAnswer();
        verify(ioService, times(questions.size())).print(acceptedAnswerMessage);
        verify(questionService, times(questions.size())).checkRightnessOfAnswerToQuestion(anyString(), any(Question.class));
        verify(ioService, times(1)).print(resultMessage);
        verify(studentMapper, times(2)).mapToStringWithNameSurnameOrder(student);
    }

}

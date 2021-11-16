package ru.otus.spring.quiz.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.quiz.domain.Answer;
import ru.otus.spring.quiz.domain.Question;
import ru.otus.spring.quiz.domain.Student;
import ru.otus.spring.quiz.exception.QuestionsReadingException;
import ru.otus.spring.quiz.mapper.StudentMapper;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class QuizServiceImplTest {

	@MockBean
	private StudentService studentService;
	@MockBean
	private QuestionService questionService;
	@MockBean
	private IOService ioService;
	@MockBean
	private InterviewerService interviewerService;
	@MockBean
	private StudentMapper studentMapper;
	@MockBean
	private L10nService l10nService;

	@Autowired
	private QuizService quizService;


	@Test
	void conductQuizShouldHappenExceptionWhenNotReadQuestions() throws QuestionsReadingException {
		Student student = new Student("Jane", "Doe");
		String studentNameSurname = student.getName() + " " + student.getSurname();
		String luckMessage = studentNameSurname + ", you're in luck!";
		String postponementMessage = "The quiz will have to be postponed.";

		when(studentService.askNameAndSurname()).thenReturn(student);
		when(questionService.getAllQuestions()).thenThrow(QuestionsReadingException.class);
		when(studentMapper.mapToStringWithNameSurnameOrder(student)).thenReturn(studentNameSurname);
		when(l10nService.getMessage(anyString(), eq(studentNameSurname))).thenReturn(luckMessage);
		when(l10nService.getMessage(anyString())).thenReturn(postponementMessage);

		quizService.conductQuiz();

		verify(studentService, times(1)).askNameAndSurname();
		verify(questionService, times(1)).getAllQuestions();
		verify(studentMapper, times(1)).mapToStringWithNameSurnameOrder(student);
		verify(l10nService, times(1)).getMessage(anyString(), eq(studentNameSurname));
		verify(l10nService, times(1)).getMessage(anyString());
		verify(ioService, times(1)).print(matches(".*" + luckMessage + ".*" + System.lineSeparator() + ".*" + postponementMessage + ".*"));
	}

	@Test
	void conductQuizShouldFinishWhenNoQuestions() throws QuestionsReadingException {
		Student student = new Student("Jane", "Doe");
		String studentNameSurname = student.getName() + " " + student.getSurname();
		String helloMessage = "Hello, " + studentNameSurname + "!";
		String noQuestionsMessage = "The sailors have no questions!";

		when(studentService.askNameAndSurname()).thenReturn(student);
		when(studentMapper.mapToStringWithNameSurnameOrder(student)).thenReturn(studentNameSurname);
		when(questionService.getAllQuestions()).thenReturn(Collections.emptyList());
		when(l10nService.getMessage(anyString(), eq(studentNameSurname))).thenReturn(helloMessage);
		when(l10nService.getMessage(anyString())).thenReturn(noQuestionsMessage);

		quizService.conductQuiz();

		verify(studentService, times(1)).askNameAndSurname();
		verify(questionService, times(1)).getAllQuestions();
		verify(studentMapper, times(1)).mapToStringWithNameSurnameOrder(student);
		verify(l10nService, times(1)).getMessage(anyString(), eq(studentNameSurname));
		verify(ioService, times(1)).print(contains(helloMessage));
		verify(l10nService, times(1)).getMessage(anyString());
		verify(ioService, times(1)).print(contains(noQuestionsMessage));
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
		String helloMessage = "Hello, " + studentNameSurname + "!";
		String beginQuizMessage = "Let's begin quiz...";
		String acceptedAnswerMessage = "Answer accepted.";
		String grade = "2";
		String resultMessage = studentNameSurname + ", your grade is " + grade + ".";

		when(studentService.askNameAndSurname()).thenReturn(student);
		when(questionService.getAllQuestions()).thenReturn(questions);
		when(studentMapper.mapToStringWithNameSurnameOrder(student)).thenReturn(studentNameSurname);
		when(l10nService.getMessage(anyString(), eq(studentNameSurname))).thenReturn(helloMessage);
		when(l10nService.getMessage(contains("begin"))).thenReturn(beginQuizMessage);
		doReturn(" ", "2", "Right answer", "3").when(interviewerService).acceptAnswer();
		when(l10nService.getMessage(contains("accepted"))).thenReturn(acceptedAnswerMessage);
		when(questionService.checkRightnessOfAnswerToQuestion(anyString(), any(Question.class))).thenReturn(false, true, true, false);
		when(l10nService.getMessage(anyString(), eq(studentNameSurname), eq(grade))).thenReturn(resultMessage);

		quizService.conductQuiz();

		verify(studentService, times(1)).askNameAndSurname();
		verify(questionService, times(1)).getAllQuestions();
		verify(l10nService, times(1)).getMessage(anyString(), eq(studentNameSurname));
		verify(ioService, times(1)).print(contains(helloMessage));
		verify(l10nService, times(1)).getMessage(contains("begin"));
		verify(ioService, times(1)).print(contains(beginQuizMessage));
		verify(interviewerService, times(questions.size())).askQuestion(anyInt(), any(Question.class));
		verify(interviewerService, times(questions.size())).acceptAnswer();
		verify(l10nService, times(questions.size())).getMessage(contains("accepted"));
		verify(ioService, times(questions.size())).print(contains(acceptedAnswerMessage));
		verify(questionService, times(questions.size())).checkRightnessOfAnswerToQuestion(anyString(), any(Question.class));
		verify(l10nService, times(1)).getMessage(anyString(), eq(studentNameSurname), eq(grade));
		verify(ioService, times(1)).print(contains(resultMessage));
		verify(studentMapper, times(2)).mapToStringWithNameSurnameOrder(student);
	}

}

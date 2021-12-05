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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = QuizServiceImpl.class)
class QuizServiceImplTest {

	@MockBean
	private StudentService studentService;
	@MockBean
	private QuestionService questionService;
	@MockBean
	private LocalizationIOService localizationIOService;
	@MockBean
	private InterviewerService interviewerService;
	@MockBean
	private StudentMapper studentMapper;

	@Autowired
	private QuizService quizService;


	@Test
	void greetShouldSayHello() {
		Student student = new Student("Jane", "Doe");
		String studentNameSurname = student.getName() + " " + student.getSurname();

		when(studentMapper.mapToStringWithNameSurnameOrder(student)).thenReturn(studentNameSurname);

		quizService.greet(student);

		verify(localizationIOService, times(1)).printPropertyValue(contains("hello"), eq(studentNameSurname));
	}

	@Test
	void reportThatNeedToIntroduceYourselfShouldSayAndReturnMessageAboutIntroducingNeed() {
		String property = "quiz.introduce";
		String expectedMessage = "Introducing need";

		when(localizationIOService.getMessage(contains(property))).thenReturn(expectedMessage);

		String actualMessage = quizService.reportThatNeedToIntroduceYourself();

		verify(localizationIOService, times(1)).print(expectedMessage);
		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	void reportThatAlreadyIntroducedYourselfShouldSayAndReturnMessageAboutIntroducingAlready() {
		Student student = new Student("Jane", "Doe");
		String studentNameSurname = student.getName() + " " + student.getSurname();
		String property = "quiz.already-introduce";
		String expectedMessage = "Introducing already";

		when(studentMapper.mapToStringWithNameSurnameOrder(student)).thenReturn(studentNameSurname);
		when(localizationIOService.getMessage(contains(property), eq(studentNameSurname))).thenReturn(expectedMessage);

		String actualMessage = quizService.reportThatAlreadyIntroducedYourself(student);

		verify(localizationIOService, times(1)).print(expectedMessage);
		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	void conductQuizShouldHappenExceptionWhenNotReadQuestions() throws QuestionsReadingException {
		Student student = new Student("Jane", "Doe");
		String studentNameSurname = student.getName() + " " + student.getSurname();

		when(questionService.getAllQuestions()).thenThrow(QuestionsReadingException.class);
		when(studentMapper.mapToStringWithNameSurnameOrder(student)).thenReturn(studentNameSurname);

		quizService.conductQuiz(student);

		verify(questionService, times(1)).getAllQuestions();
		verify(studentMapper, times(1)).mapToStringWithNameSurnameOrder(student);
		verify(localizationIOService, times(1)).printPropertyValue(anyString(), eq(studentNameSurname));
		verify(localizationIOService, times(1)).printPropertyValue(anyString());
	}

	@Test
	void conductQuizShouldFinishWhenNoQuestions() throws QuestionsReadingException {
		Student student = new Student("Jane", "Doe");

		when(questionService.getAllQuestions()).thenReturn(Collections.emptyList());

		quizService.conductQuiz(student);

		verify(questionService, times(1)).getAllQuestions();
		verify(localizationIOService, times(1)).printPropertyValue(anyString());
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
		String grade = "2";

		when(questionService.getAllQuestions()).thenReturn(questions);
		when(studentMapper.mapToStringWithNameSurnameOrder(student)).thenReturn(studentNameSurname);
		doReturn(" ", "2", "Right answer", "3").when(interviewerService).acceptAnswer();
		when(questionService.checkRightnessOfAnswerToQuestion(anyString(), any(Question.class))).thenReturn(false, true, true, false);

		quizService.conductQuiz(student);

		verify(questionService, times(1)).getAllQuestions();
		verify(localizationIOService, times(1)).printPropertyValue(contains("begin"));
		verify(interviewerService, times(questions.size())).askQuestion(anyInt(), any(Question.class));
		verify(interviewerService, times(questions.size())).acceptAnswer();
		verify(localizationIOService, times(questions.size())).printPropertyValue(contains("accepted"));
		verify(questionService, times(questions.size())).checkRightnessOfAnswerToQuestion(anyString(), any(Question.class));
		verify(localizationIOService, times(1)).printPropertyValue(contains("grade"), eq(studentNameSurname), eq(grade));
		verify(studentMapper, times(1)).mapToStringWithNameSurnameOrder(student);
	}

}

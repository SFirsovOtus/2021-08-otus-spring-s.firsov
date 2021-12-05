package ru.otus.spring.quiz.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.CommandNotCurrentlyAvailable;
import org.springframework.shell.Shell;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring.quiz.domain.Student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class ShellCommandServiceImplTest {

	@MockBean
	private StudentService studentService;
	@MockBean
	private QuizService quizService;

	@Autowired
	private ShellCommandService shellCommandService;
	@Autowired
	private Shell shell;


	private static final String COMMAND_LOGIN = "login";
	private static final String COMMAND_BEGIN = "begin";

	@Test
	void loginShouldRejectWhenStudentNameIsBlank() {
		Student student = new Student("  ", "Doe");

		when(studentService.askNameAndSurname()).thenReturn(student);

		shellCommandService.login();

		verify(quizService, times(1)).reportThatNeedToIntroduceYourself();
	}

	@Test
	void loginShouldRejectWhenStudentSurnameIsBlank() {
		Student student = new Student("Jane", "  ");

		when(studentService.askNameAndSurname()).thenReturn(student);

		shellCommandService.login();

		verify(quizService, times(1)).reportThatNeedToIntroduceYourself();
	}

	@Test
	void loginShouldGreetWhenStudentIntroducedYourself() {
		Student student = new Student("Jane", "Doe");

		when(studentService.askNameAndSurname()).thenReturn(student);

		shellCommandService.login();

		verify(quizService, times(1)).greet(student);
	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	void loginShouldRejectWhenStudentAlreadyIntroducedYourself() {
		Student student = new Student("Jane", "Doe");
		String rejectText = "Reject";

		when(studentService.askNameAndSurname()).thenReturn(student);
		doNothing().when(quizService).greet(student);
		when(quizService.reportThatAlreadyIntroducedYourself(student)).thenReturn(rejectText);

		shell.evaluate(() -> COMMAND_LOGIN);
		CommandNotCurrentlyAvailable result = (CommandNotCurrentlyAvailable) shell.evaluate(() -> COMMAND_LOGIN);
		String resultText = result.getAvailability().getReason();

		assertEquals(rejectText, resultText);
	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	void beginShouldRejectIfUserDoesNotLogin() {
		String rejectText = "Reject";
		when(quizService.reportThatNeedToIntroduceYourself()).thenReturn(rejectText);

		CommandNotCurrentlyAvailable result = (CommandNotCurrentlyAvailable) shell.evaluate(() -> COMMAND_BEGIN);
		String resultText = result.getAvailability().getReason();

		assertEquals(rejectText, resultText);
	}

}

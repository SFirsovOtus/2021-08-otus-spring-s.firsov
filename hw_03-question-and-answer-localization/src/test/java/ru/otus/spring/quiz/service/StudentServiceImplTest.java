package ru.otus.spring.quiz.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.quiz.domain.Student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class StudentServiceImplTest {

	@MockBean
	private IOService ioService;
	@MockBean
	private L10nService l10nService;

	@Autowired
	private StudentService studentService;


	@Test
	void askNameAndSurnameShouldStudentWithAcceptedNameAndSurname() {
		Student expectedStudent = new Student("Jane", "Doe");
		String propertyAboutName = "student.ask-name";
		String propertyAboutSurname = "student.ask-surname";
		String questionAboutName = "What's your name?";
		String questionAboutSurname = "What's your surname?";

		doReturn(questionAboutName).when(l10nService).getMessage(propertyAboutName);
		doReturn(questionAboutSurname).when(l10nService).getMessage(propertyAboutSurname);
		doNothing().when(ioService).print(anyString());
		doReturn(expectedStudent.getName(), expectedStudent.getSurname()).when(ioService).scan();

		Student actualStudent = studentService.askNameAndSurname();

		verify(l10nService, times(1)).getMessage(propertyAboutName);
		verify(l10nService, times(1)).getMessage(propertyAboutSurname);
		verify(ioService, times(1)).print(questionAboutName);
		verify(ioService, times(1)).print(questionAboutSurname);
		verify(ioService, times(2)).scan();
		assertEquals(expectedStudent.getName(), actualStudent.getName());
		assertEquals(expectedStudent.getSurname(), actualStudent.getSurname());
	}

}

package ru.otus.spring.quiz.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.quiz.domain.Student;
import ru.otus.spring.quiz.facade.L10nIOFacade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class StudentServiceImplTest {

	@MockBean
	private L10nIOFacade l10nIOFacade;

	@Autowired
	private StudentService studentService;


	@Test
	void askNameAndSurnameShouldStudentWithAcceptedNameAndSurname() {
		Student expectedStudent = new Student("Jane", "Doe");
		String propertyAboutName = "student.ask-name";
		String propertyAboutSurname = "student.ask-surname";

		doNothing().when(l10nIOFacade).print(anyString());
		doReturn(expectedStudent.getName(), expectedStudent.getSurname()).when(l10nIOFacade).scan();

		Student actualStudent = studentService.askNameAndSurname();

		verify(l10nIOFacade, times(1)).printPropertyValue(propertyAboutName);
		verify(l10nIOFacade, times(1)).printPropertyValue(propertyAboutSurname);
		verify(l10nIOFacade, times(2)).scan();
		assertEquals(expectedStudent.getName(), actualStudent.getName());
		assertEquals(expectedStudent.getSurname(), actualStudent.getSurname());
	}

}

package ru.otus.spring.quiz.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.quiz.domain.Student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = StudentServiceImpl.class)
class StudentServiceImplTest {

	@MockBean
	private LocalizationIOService localizationIOService;

	@Autowired
	private StudentService studentService;


	@Test
	void askNameAndSurnameShouldStudentWithAcceptedNameAndSurname() {
		Student expectedStudent = new Student("Jane", "Doe");
		String propertyAboutName = "student.ask-name";
		String propertyAboutSurname = "student.ask-surname";

		doNothing().when(localizationIOService).print(anyString());
		doReturn(expectedStudent.getName(), expectedStudent.getSurname()).when(localizationIOService).scan();

		Student actualStudent = studentService.askNameAndSurname();

		verify(localizationIOService, times(1)).printPropertyValue(propertyAboutName);
		verify(localizationIOService, times(1)).printPropertyValue(propertyAboutSurname);
		verify(localizationIOService, times(2)).scan();
		assertEquals(expectedStudent.getName(), actualStudent.getName());
		assertEquals(expectedStudent.getSurname(), actualStudent.getSurname());
	}

}

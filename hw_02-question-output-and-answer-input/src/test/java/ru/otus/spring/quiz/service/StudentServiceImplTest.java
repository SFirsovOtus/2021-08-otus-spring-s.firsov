package ru.otus.spring.quiz.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.quiz.domain.Student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private ConsoleService consoleService;

    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentServiceImpl(consoleService);
    }


    @Test
    void askNameAndSurnameShouldStudentWithAcceptedNameAndSurname() {
        Student expectedStudent = new Student("Jane", "Doe");
        String questionAboutName = "What's your name?";
        String questionAboutSurname = "What's your surname?";

        doNothing().when(consoleService).print(anyString());
        doReturn(expectedStudent.getName(), expectedStudent.getSurname()).when(consoleService).scan();

        Student actualStudent = studentService.askNameAndSurname();

        verify(consoleService, times(1)).print(questionAboutName);
        verify(consoleService, times(1)).print(questionAboutSurname);
        verify(consoleService, times(2)).scan();
        assertEquals(expectedStudent.getName(), actualStudent.getName());
        assertEquals(expectedStudent.getSurname(), actualStudent.getSurname());
    }

}

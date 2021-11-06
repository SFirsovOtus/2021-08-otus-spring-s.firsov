package ru.otus.spring.quiz.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.quiz.domain.Student;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final ConsoleService consoleService;


    private String askName() {
        consoleService.print("What's your name?");
        return consoleService.scan();
    }

    private String askSurname() {
        consoleService.print("What's your surname?");
        return consoleService.scan();
    }


    @Override
    public Student askNameAndSurname() {
        String name = askName();
        String surname = askSurname();

        return new Student(name, surname);
    }

}

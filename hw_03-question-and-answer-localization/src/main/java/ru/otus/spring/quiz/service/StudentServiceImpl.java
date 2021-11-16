package ru.otus.spring.quiz.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.quiz.domain.Student;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final IOService ioService;
    private final L10nService l10nService;


    private String askName() {
        ioService.print(l10nService.getMessage("student.ask-name"));
        return ioService.scan();
    }

    private String askSurname() {
        ioService.print(l10nService.getMessage("student.ask-surname"));
        return ioService.scan();
    }


    @Override
    public Student askNameAndSurname() {
        String name = askName();
        String surname = askSurname();

        return new Student(name, surname);
    }

}

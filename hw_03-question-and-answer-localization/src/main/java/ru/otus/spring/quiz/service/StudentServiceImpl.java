package ru.otus.spring.quiz.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.quiz.domain.Student;
import ru.otus.spring.quiz.facade.L10nIOFacade;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final L10nIOFacade l10nIOFacade;


    private String askName() {
        l10nIOFacade.printPropertyValue("student.ask-name");
        return l10nIOFacade.scan();
    }

    private String askSurname() {
        l10nIOFacade.printPropertyValue("student.ask-surname");
        return l10nIOFacade.scan();
    }


    @Override
    public Student askNameAndSurname() {
        String name = askName();
        String surname = askSurname();

        return new Student(name, surname);
    }

}

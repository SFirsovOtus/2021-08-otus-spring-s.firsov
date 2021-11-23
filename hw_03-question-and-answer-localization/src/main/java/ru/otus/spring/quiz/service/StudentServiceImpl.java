package ru.otus.spring.quiz.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.quiz.domain.Student;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final LocalizationIOService localizationIOService;


    private String askName() {
        localizationIOService.printPropertyValue("student.ask-name");
        return localizationIOService.scan();
    }

    private String askSurname() {
        localizationIOService.printPropertyValue("student.ask-surname");
        return localizationIOService.scan();
    }


    @Override
    public Student askNameAndSurname() {
        String name = askName();
        String surname = askSurname();

        return new Student(name, surname);
    }

}

package ru.otus.spring.quiz.mapper;

import org.springframework.stereotype.Service;
import ru.otus.spring.quiz.domain.Student;

@Service
public class StudentMapper {

    public String mapToStringWithNameSurnameOrder(Student student) {
        return String.format("%s %s", student.getName(), student.getSurname());
    }

}

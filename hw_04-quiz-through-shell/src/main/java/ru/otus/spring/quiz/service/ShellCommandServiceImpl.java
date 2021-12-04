package ru.otus.spring.quiz.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.spring.quiz.config.CommandLineInterfaceConfig;
import ru.otus.spring.quiz.domain.Student;

@ShellComponent
@ShellCommandGroup(CommandLineInterfaceConfig.SHELL_COMMAND_GROUP_QUIZ_COMMANDS)
@RequiredArgsConstructor
public class ShellCommandServiceImpl implements ShellCommandService {

    private final StudentService studentService;
    private final QuizService quizService;

    private Student loggedInStudent;

    private boolean existsLoggedInUser() {
        return this.loggedInStudent != null;
    }

    private Availability needToLogin() {
        if(!existsLoggedInUser()) {
            return Availability.available();
        } else {
            return Availability.unavailable(quizService.reportThatAlreadyIntroducedYourself(this.loggedInStudent));
        }
    }

    private Availability canBegin() {
        if(existsLoggedInUser()) {
            return Availability.available();
        } else {
            return Availability.unavailable(quizService.reportThatNeedToIntroduceYourself());
        }
    }

    @Override
    @ShellMethod(value = "Login command (need to enter name and surname)", key = "login")
    @ShellMethodAvailability(value = "needToLogin")
    public void login() {
        Student student = studentService.askNameAndSurname();

        if (StringUtils.EMPTY.equals(student.getName().trim()) ||
                StringUtils.EMPTY.equals(student.getSurname().trim())) {
            quizService.reportThatNeedToIntroduceYourself();
        } else {
            this.loggedInStudent = student;
            quizService.greet(this.loggedInStudent);
        }
    }

    @Override
    @ShellMethod(value = "Begin quiz (need to answer questions)", key = "begin")
    @ShellMethodAvailability("canBegin")
    public void begin() {
        quizService.conductQuiz(this.loggedInStudent);
    }

}

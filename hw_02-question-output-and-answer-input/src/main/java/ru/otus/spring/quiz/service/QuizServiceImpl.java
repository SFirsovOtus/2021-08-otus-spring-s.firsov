package ru.otus.spring.quiz.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.quiz.domain.Question;
import ru.otus.spring.quiz.domain.Student;
import ru.otus.spring.quiz.exception.QuestionsReadingException;
import ru.otus.spring.quiz.mapper.StudentMapper;

import java.util.List;

@Service
@AllArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final StudentService studentService;
    private final QuestionService questionService;
    private final IOService ioService;
    private final InterviewerService interviewerService;
    private final StudentMapper studentMapper;


    private void reportAboutPostponement(Student student) {
        ioService.print(System.lineSeparator() +
                studentMapper.mapToStringWithNameSurnameOrder(student) + ", you're in luck!" + System.lineSeparator() +
                "The quiz will have to be postponed." + System.lineSeparator());
    }

    private void greet(Student student) {
        ioService.print(System.lineSeparator() +
                "Hello, " + studentMapper.mapToStringWithNameSurnameOrder(student) + "!");
    }

    private void reportAboutNoQuestions() {
        ioService.print("The sailors have no questions!" + System.lineSeparator());
    }

    private void reportAboutQuizBeginning() {
        ioService.print("Let's begin quiz..." + System.lineSeparator());
    }

    private int askQuestionsAndCalculateGrade(List<Question> questions) {
        int grade = 0;

        for(int i = 0; i < questions.size(); i++) {
            Question askedQuestion = questions.get(i);
            interviewerService.askQuestion(i + 1, askedQuestion);  // "+ 1", т. к. нумерация вопросов начинается с 1 в отличие от индексов списка, которые начинаются с 0
            String acceptedAnswer = interviewerService.acceptAnswer();
            ioService.print("Answer accepted." + System.lineSeparator() + System.lineSeparator());
            if (questionService.checkRightnessOfAnswerToQuestion(acceptedAnswer, askedQuestion)) {
                grade++;
            }
        }

        return grade;
    }

    private void reportResult(Student student, int grade) {
        ioService.print(studentMapper.mapToStringWithNameSurnameOrder(student) + ", your grade is " + grade + ".");
    }


    @Override
    public void conductQuiz() {
        Student student = studentService.askNameAndSurname();

        List<Question> questions;
        try {
            questions = questionService.getAllQuestions();
        } catch (QuestionsReadingException exception) {
            reportAboutPostponement(student);
            exception.printStackTrace();
            return;
        }

        greet(student);

        if (questions.isEmpty()) {
            reportAboutNoQuestions();
            return;
        }

        reportAboutQuizBeginning();

        int grade = askQuestionsAndCalculateGrade(questions);

        reportResult(student, grade);
    }

}

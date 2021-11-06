package ru.otus.spring.quiz.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.quiz.domain.Question;
import ru.otus.spring.quiz.domain.Student;
import ru.otus.spring.quiz.exception.QuestionsNotFoundException;
import ru.otus.spring.quiz.exception.QuestionsReadingException;

import java.util.List;

@Service
@AllArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final StudentService studentService;
    private final QuestionService questionService;
    private final ConsoleService consoleService;


    private List<Question> getAllQuestions(Student student) throws QuestionsReadingException {
        List<Question> questions = null;

        try {
            questions =  questionService.getAllQuestions();
        } catch (QuestionsReadingException exception) {
            consoleService.print(System.lineSeparator() +
                    student + ", you're in luck!" + System.lineSeparator() +
                    "The quiz will have to be postponed." + System.lineSeparator());
            throw exception;
        }

        return questions;
    }

    private void greet(Student student, List<Question> questions) throws QuestionsNotFoundException {
        consoleService.print(System.lineSeparator() +
                "Hello, " + student + "!");

        if (questions.isEmpty()) {
            consoleService.print("The sailors have no questions!" + System.lineSeparator());
            throw new QuestionsNotFoundException();
        }

        consoleService.print("Let's begin quiz..." + System.lineSeparator());
    }

    private int askQuestionsAndCalculateGrade(List<Question> questions) {
        Question askedQuestion = null;
        String acceptedAnswer = null;
        int grade = 0;

        for(int i = 0; i < questions.size(); i++) {
            askedQuestion = questions.get(i);
            questionService.askQuestion(i + 1, askedQuestion);  // "+ 1", т. к. нумерация вопросов начинается с 1 в отличие от индексов списка, которые начинаются с 0
            acceptedAnswer = questionService.acceptAnswer();
            consoleService.print("Answer accepted." + System.lineSeparator() + System.lineSeparator());
            if (questionService.checkRightnessOfAnswerToQuestion(acceptedAnswer, askedQuestion)) {
                grade++;
            }
        }

        return grade;
    }

    private void reportResult(Student student, int grade) {
        consoleService.print(student + ", your grade is " + grade + ".");
    }


    @Override
    public void conductQuiz() throws QuestionsReadingException, QuestionsNotFoundException {
        Student student = studentService.askNameAndSurname();

        List<Question> questions = getAllQuestions(student);

        greet(student, questions);

        int grade = askQuestionsAndCalculateGrade(questions);

        reportResult(student, grade);
    }

}

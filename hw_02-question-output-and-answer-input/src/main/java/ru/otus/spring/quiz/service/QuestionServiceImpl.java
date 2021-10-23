package ru.otus.spring.quiz.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.quiz.dao.QuestionDao;
import ru.otus.spring.quiz.domain.Answer;
import ru.otus.spring.quiz.domain.Question;
import ru.otus.spring.quiz.domain.Student;

import java.util.List;
import java.util.Scanner;

@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao questionDao;


    public List<Question> getAll() {
        return questionDao.readAll();
    }

    public Integer printSingle(Question question) {
        Integer rightVariantNumber = 0;

        System.out.println(question.getFormulation());

        int i = 0;
        for (Answer.Variant variant : question.getAnswer().getVariants()) {
            i++;
            System.out.println("    " + i + ". " + variant.getFormulation());
            if (variant.getIsRight()) {
                rightVariantNumber = i;
            }
        }

        System.out.println();
        return rightVariantNumber;
    }

    public void printBulk(List<Question> questions) {
        int i = 0;
        for (Question question : questions) {
            i++;
            printSingle(new Question(i + ". " + question.getFormulation(), question.getAnswer()));
        }
    }

    public Integer printAll() {
        List<Question> questions = getAll();
        printBulk(questions);
        return questions.size();
    }

    public Student greet() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("What's your name?");
        String name = scanner.nextLine();

        System.out.println("What's your surname?");
        String surname = scanner.nextLine();

        System.out.println(System.lineSeparator() + "Hello, " + name + " " + surname + "!");
        return new Student(name, surname);
    }

    public void conductQuiz() {
        Student student = greet();

        List<Question> questions = getAll();
        if (questions.isEmpty()) {
            System.out.println("The sailors have no questions!");
            return;
        }

        System.out.println("Let's begin quiz..." + System.lineSeparator());

        Scanner scanner = new Scanner(System.in);
        String answer;
        Integer rightAnswer;
        Integer grade = 0;
        int questionCounter = 0;
        for (Question question : questions) {
            questionCounter++;
            question = new Question(questionCounter + ". " + question.getFormulation(), question.getAnswer());
            rightAnswer = printSingle(question);
            answer = scanner.nextLine();
            System.out.println("Answer accepted." + System.lineSeparator() + System.lineSeparator());

            // на вопрос ответили правильно, если
            // вопрос без вариантов и что-нибудь ввели в качестве ответа
            // или выбранный вариант ответа совпал с правильным вариантом
            if ((rightAnswer == 0 && !answer.isEmpty()) ||
                    answer.equals(rightAnswer.toString())) {
                grade++;
            }
        }

        System.out.println(student.getName() + " " + student.getSurname() + ", your grade is " + grade);
    }

}

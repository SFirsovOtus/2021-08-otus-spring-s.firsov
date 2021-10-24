package ru.otus.spring.quiz.service;

import ru.otus.spring.quiz.dao.QuestionDao;
import ru.otus.spring.quiz.domain.Answer;
import ru.otus.spring.quiz.domain.Question;

import java.util.List;

public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao questionDao;

    public QuestionServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }


    public List<Question> getAll() {
        return questionDao.readAll();
    }

    public void printSingle(Question question) {
        System.out.println(question.getFormulation());

        int i = 0;
        for (Answer.Variant variant: question.getAnswer().getVariants()) {
            i++;
            System.out.println("    " + i + ". " + variant.getFormulation());
        }

        System.out.println();
    }

    public void printBulk(List<Question> questions) {
        int i = 0;
        for (Question question: questions) {
            i++;
            printSingle(new Question(i + ". " + question.getFormulation(), question.getAnswer()));
        }
    }

    public Integer printAll() {
        List<Question> questions = getAll();
        printBulk(questions);
        return questions.size();
    }

}

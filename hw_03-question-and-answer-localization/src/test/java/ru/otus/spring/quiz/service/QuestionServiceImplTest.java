package ru.otus.spring.quiz.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.quiz.dao.QuestionDao;
import ru.otus.spring.quiz.domain.Answer;
import ru.otus.spring.quiz.domain.Question;
import ru.otus.spring.quiz.exception.QuestionsReadingException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class QuestionServiceImplTest {

	@MockBean
	private QuestionDao questionDao;

	@Autowired
	private QuestionService questionService;


	@Test
	void getAllQuestionsShouldExecuteReadAllOfQuestionDao() throws QuestionsReadingException {
		questionService.getAllQuestions();
		verify(questionDao, times(1)).readAll();
	}

	@Test
	void checkRightnessOfAnswerToQuestionWhenChosenRightVariant() {
		List<Answer.Variant> variants = List.of(
				new Answer.Variant("This variant is bad", false),
				new Answer.Variant("This variant is good", true)
		);
		Question askedQuestion = new Question("Strange question", new Answer(variants));
		String acceptedAnswer = "  2 ";

		boolean right = questionService.checkRightnessOfAnswerToQuestion(acceptedAnswer, askedQuestion);

		assertTrue(right);
	}

	@Test
	void checkRightnessOfAnswerToQuestionWhenChosenNotRightVariant() {
		List<Answer.Variant> variants = List.of(
				new Answer.Variant("This variant is bad", false),
				new Answer.Variant("This variant is good", true)
		);
		Question askedQuestion = new Question("Strange question", new Answer(variants));
		String acceptedAnswer = "1";

		boolean right = questionService.checkRightnessOfAnswerToQuestion(acceptedAnswer, askedQuestion);

		assertFalse(right);
	}

	@Test
	void checkRightnessOfAnswerToQuestionWhenNoVariantsAndEnteredAnswer() {
		Question askedQuestion = new Question("Strange question", new Answer(Collections.emptyList()));
		String acceptedAnswer = "Strange answer";

		boolean right = questionService.checkRightnessOfAnswerToQuestion(acceptedAnswer, askedQuestion);

		assertTrue(right);
	}

	@Test
	void checkRightnessOfAnswerToQuestionWhenNoVariantsAndNoAnswer() {
		Question askedQuestion = new Question("Strange question", new Answer(Collections.emptyList()));
		String acceptedAnswer = "  ";

		boolean right = questionService.checkRightnessOfAnswerToQuestion(acceptedAnswer, askedQuestion);

		assertFalse(right);
	}

}

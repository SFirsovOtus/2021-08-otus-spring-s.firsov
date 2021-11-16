package ru.otus.spring.quiz.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.quiz.dao.QuestionDao;
import ru.otus.spring.quiz.domain.Answer;
import ru.otus.spring.quiz.domain.Question;
import ru.otus.spring.quiz.exception.QuestionsReadingException;
import ru.otus.spring.quiz.mapper.L10nMapper;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class QuestionServiceImplTest {

	@MockBean
	private QuestionDao questionDao;
	@MockBean
	private L10nMapper l10nMapper;

	@Autowired
	private QuestionService questionService;


	@Test
	void getAllQuestionsShouldExecuteReadAllOfQuestionDaoAndLocalizeQuestions() throws QuestionsReadingException {
		List<Answer.Variant> rawVariants = List.of(
				new Answer.Variant("strange-question.bad-variant", false),
				new Answer.Variant("strange-question.good-variant", true)
		);
		List<Question> rawQuestions = List.of(new Question("strange-question.formulation", new Answer(rawVariants)));
		List<Answer.Variant> localizedVariants = List.of(
				new Answer.Variant("This variant is bad", false),
				new Answer.Variant("This variant is good", true)
		);
		List<Question> localizedQuestions = List.of(new Question("Strange question", new Answer(localizedVariants)));

		doReturn(rawQuestions).when(questionDao).readAll();
		doReturn(localizedQuestions).when(l10nMapper).localizeQuestions(rawQuestions);

		List<Question> actualQuestions = questionService.getAllQuestions();

		verify(questionDao, times(1)).readAll();
		verify(l10nMapper, times(1)).localizeQuestions(rawQuestions);
		assertEquals(localizedQuestions, actualQuestions);
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

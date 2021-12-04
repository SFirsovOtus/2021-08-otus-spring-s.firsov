package ru.otus.spring.quiz.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.quiz.domain.Answer;
import ru.otus.spring.quiz.domain.Question;
import ru.otus.spring.quiz.mapper.QuestionMapper;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = InterviewerServiceImpl.class)
class InterviewerServiceImplTest {

	@MockBean
	private IOService ioService;
	@MockBean
	private QuestionMapper questionMapper;

	@Autowired
	private InterviewerService interviewerService;


	@Test
	void askQuestionShouldExecutePrintOfQuestionWithNumber() {
		int questionNumber = 123;
		Question question = new Question("Any question", new Answer(Collections.emptyList()));
		String someText = "Some text";

		doReturn(someText).when(questionMapper).mapToStringWithQuestionNumber(questionNumber, question);
		doNothing().when(ioService).print(someText);

		interviewerService.askQuestion(questionNumber, question);

		verify(questionMapper, times(1)).mapToStringWithQuestionNumber(questionNumber, question);
		verify(ioService, times(1)).print(someText);
	}

	@Test
	void acceptAnswerShouldReturnEnteredAnswer() {
		String expectedAnswer = "Any answer";

		doReturn(expectedAnswer).when(ioService).scan();

		String actualAnswer = interviewerService.acceptAnswer();

		verify(ioService, times(1)).scan();
		assertEquals(expectedAnswer, actualAnswer);
	}

}

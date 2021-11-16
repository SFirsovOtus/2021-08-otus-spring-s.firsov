package ru.otus.spring.quiz.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class L10nServiceImplTest {

	@MockBean
	private MessageSource messageSource;

	@Autowired
	private L10nService l10nService;


	@Test
	void getMessageShouldExecuteGetMessageOfMessageSource() {
		String property = "any-property";
		String arg1 = "qqq", arg2 = "www";
		String expectedMessage = "Any message";

		doReturn(expectedMessage).when(messageSource).getMessage(eq(property), eq(new String[] {arg1, arg2}), any(Locale.class));

		String actualMessage = l10nService.getMessage(property, arg1, arg2);

		verify(messageSource, times(1)).getMessage(eq(property), eq(new String[] {arg1, arg2}), any(Locale.class));
		assertEquals(expectedMessage, actualMessage);
	}

}

package ru.otus.spring.quiz.service;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import ru.otus.spring.quiz.config.LocaleConfig;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class LocalizationServiceImplTest {

	@MockBean
	private LocaleConfig localeConfig;
	@MockBean
	private MessageSource messageSource;

	@Autowired
	private LocalizationService localizationService;


	@Test
	void getMessageShouldExecuteGetMessageOfMessageSource() {
		Locale locale = Locale.forLanguageTag(StringUtils.EMPTY);
		String property = "any-property";
		String arg1 = "qqq", arg2 = "www";
		String expectedMessage = "Any message";

		doReturn(locale).when(localeConfig).getLocale();
		doReturn(expectedMessage).when(messageSource).getMessage(eq(property), eq(new String[] {arg1, arg2}), any(Locale.class));

		String actualMessage = localizationService.getMessage(property, arg1, arg2);

		verify(localeConfig, times(1)).getLocale();
		verify(messageSource, times(1)).getMessage(property, new String[] {arg1, arg2}, locale);
		assertEquals(expectedMessage, actualMessage);
	}

}

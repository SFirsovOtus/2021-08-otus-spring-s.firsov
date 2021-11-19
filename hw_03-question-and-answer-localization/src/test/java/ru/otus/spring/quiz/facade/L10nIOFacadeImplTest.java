package ru.otus.spring.quiz.facade;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.quiz.service.IOService;
import ru.otus.spring.quiz.service.L10nService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class L10nIOFacadeImplTest {

	@MockBean
	private IOService ioService;
	@MockBean
	private L10nService l10nService;

	@Autowired
	private L10nIOFacade l10nIOFacade;


	@Test
	void printShouldExecutePrintOfIOService() {
		String text = "Any text";

		doNothing().when(ioService).print(text);

		l10nIOFacade.print(text);

		verify(ioService, times(1)).print(text);
	}

	@Test
	void scanShouldExecuteScanOfIOService() {
		String expectedText = "Any text";

		doReturn(expectedText).when(ioService).scan();

		String actualText = l10nIOFacade.scan();

		verify(ioService, times(1)).scan();
		assertEquals(expectedText, actualText);
	}

	@Test
	void getMessageShouldExecuteGetMessageOfL10nService() {
		String property = "any-property";
		String[] args = new String[] {"arg1", "arg2"};
		String expectedText = "Any text";

		doReturn(expectedText).when(l10nService).getMessage(property, args);

		String actualText = l10nIOFacade.getMessage(property, args);

		verify(l10nService, times(1)).getMessage(property, args);
		assertEquals(expectedText, actualText);
	}

	@Test
	void printPropertyValueShouldExecuteGetMessageAndPrintOfServices() {
		String property = "any.property";
		String[] args = new String[] {"arg1", "arg2"};
		String text = "Any text";

		doReturn(text).when(l10nService).getMessage(property, args);
		doNothing().when(ioService).print(text);

		l10nIOFacade.printPropertyValue(property, args);

		verify(l10nService, times(1)).getMessage(property, args);
		verify(ioService, times(1)).print(text);
	}

}

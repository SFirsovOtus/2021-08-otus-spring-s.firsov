package ru.otus.spring.quiz.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {LocalizationServiceImpl.class, IOServiceImpl.class, LocalizationIOServiceImpl.class})
class LocalizationIOServiceImplTest {

	@MockBean
	private IOService ioService;
	@MockBean
	private LocalizationService localizationService;

	@Autowired
	private LocalizationIOService localizationIOService;


	@Test
	void printShouldExecutePrintOfIOService() {
		String text = "Any text";

		doNothing().when(ioService).print(text);

		localizationIOService.print(text);

		verify(ioService, times(1)).print(text);
	}

	@Test
	void scanShouldExecuteScanOfIOService() {
		String expectedText = "Any text";

		doReturn(expectedText).when(ioService).scan();

		String actualText = localizationIOService.scan();

		verify(ioService, times(1)).scan();
		assertEquals(expectedText, actualText);
	}

	@Test
	void getMessageShouldExecuteGetMessageOfL10nService() {
		String property = "any-property";
		String[] args = new String[] {"arg1", "arg2"};
		String expectedText = "Any text";

		doReturn(expectedText).when(localizationService).getMessage(property, args);

		String actualText = localizationIOService.getMessage(property, args);

		verify(localizationService, times(1)).getMessage(property, args);
		assertEquals(expectedText, actualText);
	}

	@Test
	void printPropertyValueShouldExecuteGetMessageAndPrintOfServices() {
		String property = "any.property";
		String[] args = new String[] {"arg1", "arg2"};
		String text = "Any text";

		doReturn(text).when(localizationService).getMessage(property, args);
		doNothing().when(ioService).print(text);

		localizationIOService.printPropertyValue(property, args);

		verify(localizationService, times(1)).getMessage(property, args);
		verify(ioService, times(1)).print(text);
	}

}

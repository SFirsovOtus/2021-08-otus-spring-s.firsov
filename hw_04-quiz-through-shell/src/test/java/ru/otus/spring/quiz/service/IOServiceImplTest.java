package ru.otus.spring.quiz.service;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IOServiceImplTest {

	@Test
	void scanShouldReadEnteredLine() {
		String expectedLine = "Any line";

		ByteArrayInputStream inputStream = new ByteArrayInputStream(expectedLine.getBytes());
		IOService ioService = new IOServiceImpl(inputStream, System.out);

		String actualLine = ioService.scan();

		assertEquals(expectedLine, actualLine);
	}

	@Test
	void printShouldWriteGivenLine() {
		String expectedLine = "Any line";

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		IOService ioService = new IOServiceImpl(System.in, new PrintStream(outputStream));

		ioService.print(expectedLine);

		String actualLine = outputStream.toString().replace(System.lineSeparator(), StringUtils.EMPTY);

		assertEquals(expectedLine, actualLine);
	}

}

package ru.otus.spring.quiz.service;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConsoleServiceImplTest {

    @Test
    void scanShouldReadEnteredLine() {
        String expectedLine = "Any line";

        ByteArrayInputStream inputStream = new ByteArrayInputStream(expectedLine.getBytes());
        ConsoleService consoleService = new ConsoleServiceImpl(inputStream, System.out);

        String actualLine = consoleService.scan();

        assertEquals(expectedLine, actualLine);
    }

    @Test
    void printShouldWriteGivenLine() {
        String expectedLine = "Any line";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ConsoleService consoleService = new ConsoleServiceImpl(System.in, new PrintStream(outputStream));

        consoleService.print(expectedLine);

        String actualLine = outputStream.toString().replace(System.lineSeparator(), "");

        assertEquals(expectedLine, actualLine);
    }

}

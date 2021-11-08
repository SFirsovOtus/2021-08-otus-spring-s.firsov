package ru.otus.spring.quiz.service;

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
        IOService IOService = new IOServiceImpl(inputStream, System.out);

        String actualLine = IOService.scan();

        assertEquals(expectedLine, actualLine);
    }

    @Test
    void printShouldWriteGivenLine() {
        String expectedLine = "Any line";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        IOService IOService = new IOServiceImpl(System.in, new PrintStream(outputStream));

        IOService.print(expectedLine);

        String actualLine = outputStream.toString().replace(System.lineSeparator(), "");

        assertEquals(expectedLine, actualLine);
    }

}

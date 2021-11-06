package ru.otus.spring.quiz.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Service
public class ConsoleServiceImpl implements ConsoleService {

    private final Scanner input;
    private final PrintStream output;

    public ConsoleServiceImpl(@Value("#{T(System).in}") InputStream inputStream, @Value("#{T(System).out}") PrintStream outputStream) {
        this.input = new Scanner(inputStream);
        this.output = outputStream;
    }


    @Override
    public String scan() {
        return input.nextLine();
    }

    @Override
    public void print(Object object) {
        output.println(object);
    }

}

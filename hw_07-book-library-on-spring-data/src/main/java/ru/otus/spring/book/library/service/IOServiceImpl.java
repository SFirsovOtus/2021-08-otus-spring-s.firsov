package ru.otus.spring.book.library.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.PrintStream;

@Service
public class IOServiceImpl implements IOService {

    private final PrintStream output;

    public IOServiceImpl(@Value("#{T(System).out}") PrintStream outputStream) {
        this.output = outputStream;
    }


    @Override
    public void print(Object object) {
        output.println(object);
    }

}

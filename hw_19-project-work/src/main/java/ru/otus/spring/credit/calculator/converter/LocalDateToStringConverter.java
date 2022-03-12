package ru.otus.spring.credit.calculator.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;

public class LocalDateToStringConverter implements Converter<LocalDate, String> {

    @Override
    public String convert(LocalDate localDate) {
        return localDate.toString();
    }

}

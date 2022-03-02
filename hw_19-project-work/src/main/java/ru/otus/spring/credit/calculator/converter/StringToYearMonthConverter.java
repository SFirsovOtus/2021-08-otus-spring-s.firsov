package ru.otus.spring.credit.calculator.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.YearMonth;

public class StringToYearMonthConverter implements Converter<String, YearMonth> {

    @Override
    public YearMonth convert(String string) {
        String[] yearAndMonth = string.split("-");

        int year = Integer.parseInt(yearAndMonth[0]);
        int month = Integer.parseInt(yearAndMonth[1]);

        return YearMonth.of(year, month);
    }

}

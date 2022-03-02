package ru.otus.spring.credit.calculator.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.YearMonth;

public class YearMonthToStringConverter implements Converter<YearMonth, String> {

    @Override
    public String convert(YearMonth yearMonth) {
        return yearMonth.toString();
    }

}

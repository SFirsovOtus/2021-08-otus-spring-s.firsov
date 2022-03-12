package ru.otus.spring.credit.calculator.service.calculation;

import java.time.LocalDate;
import java.time.YearMonth;

public interface DateCalculationService {

    int getDaysNumberInYear(int year);

    int getDaysNumberInMonth(YearMonth yearMonth);

    LocalDate getLastDateOfMonth(YearMonth yearMonth);

    int getDaysBetweenTwoDates(LocalDate startDate, LocalDate endDate);

    int getDaysNumberUntilEndOfMonth(LocalDate date);

}

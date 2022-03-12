package ru.otus.spring.credit.calculator.service.calculation;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;

@Service
public class DateCalculationServiceImpl implements DateCalculationService {

    public static final int DAYS_NUMBER_IN_NON_LEAP_YEAR = 365;
    public static final int DAYS_NUMBER_IN_LEAP_YEAR = 366;
    public static final int MONTHS_NUMBER_IN_YEAR = 12;

    @Override
    public int getDaysNumberInYear(int year) {
        return Year.isLeap(year) ? DAYS_NUMBER_IN_LEAP_YEAR : DAYS_NUMBER_IN_NON_LEAP_YEAR;
    }

    @Override
    public int getDaysNumberInMonth(YearMonth yearMonth) {
        return yearMonth.lengthOfMonth();
    }

    @Override
    public LocalDate getLastDateOfMonth(YearMonth yearMonth) {
        return yearMonth.atEndOfMonth();
    }

    @Override
    public int getDaysBetweenTwoDates(LocalDate startDate, LocalDate endDate) {
        return (int) ChronoUnit.DAYS.between(startDate, endDate);
    }

    @Override
    public int getDaysNumberUntilEndOfMonth(LocalDate date) {
        return date.lengthOfMonth() - date.getDayOfMonth();
    }

}

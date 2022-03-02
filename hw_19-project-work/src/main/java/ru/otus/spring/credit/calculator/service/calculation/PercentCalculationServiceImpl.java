package ru.otus.spring.credit.calculator.service.calculation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static ru.otus.spring.credit.calculator.service.calculation.DateCalculationServiceImpl.MONTHS_NUMBER_IN_YEAR;

@Service
@RequiredArgsConstructor
public class PercentCalculationServiceImpl implements PercentCalculationService {

    private final DateCalculationService dateCalculationService;


    public static final int ONE_HUNDRED_PERCENTS = 100;
    public static final int PERCENT_PRECISION = 10;

    @Override
    public BigDecimal getSharePercentForMonth(BigDecimal yearPercent) {
        return yearPercent
                .divide(BigDecimal.valueOf(ONE_HUNDRED_PERCENTS), PERCENT_PRECISION, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(MONTHS_NUMBER_IN_YEAR), PERCENT_PRECISION, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal getSharePercentForDate(BigDecimal yearPercent, LocalDate date) {
        int year = date.getYear();
        int daysNumberInYear = dateCalculationService.getDaysNumberInYear(year);

        return yearPercent
                .divide(BigDecimal.valueOf(ONE_HUNDRED_PERCENTS), PERCENT_PRECISION, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(daysNumberInYear), PERCENT_PRECISION, RoundingMode.HALF_UP);
    }

}

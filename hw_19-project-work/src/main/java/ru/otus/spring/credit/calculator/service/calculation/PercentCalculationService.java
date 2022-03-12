package ru.otus.spring.credit.calculator.service.calculation;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface PercentCalculationService {

    BigDecimal getSharePercentForMonth(BigDecimal yearPercent);

    BigDecimal getSharePercentForDate(BigDecimal yearPercent, LocalDate date);

}

package ru.otus.spring.credit.calculator.service.calculation;

import ru.otus.spring.credit.calculator.domain.MonthlyPayment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;

public interface MonthlyPaymentCalculationService {

    MonthlyPayment getMonthlyPaymentForFullMonth(YearMonth yearMonth,
                                                 BigDecimal bodyAmountRemainder,
                                                 BigDecimal yearPercent,
                                                 BigDecimal monthlyPaymentAmount);

    MonthlyPayment getMonthlyPaymentForPartOfMonth(LocalDate earlyPaymentDate,
                                                   BigDecimal bodyAmountRemainder,
                                                   BigDecimal yearPercent,
                                                   BigDecimal monthlyPaymentAmount);

}

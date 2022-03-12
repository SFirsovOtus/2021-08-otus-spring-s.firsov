package ru.otus.spring.credit.calculator.service.calculation;

import ru.otus.spring.credit.calculator.domain.EarlyPayment;
import ru.otus.spring.credit.calculator.domain.MonthlyPayment;
import ru.otus.spring.credit.calculator.domain.Schedule;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface AmountCalculationService {

    BigDecimal getDailyPercentAmount(BigDecimal bodyAmount, BigDecimal yearPercent, LocalDate date);

    BigDecimal getAnnuityCoefficient(BigDecimal yearPercent, int monthsNumber);

    BigDecimal getMonthlyPaymentAmount(BigDecimal creditAmount, BigDecimal annuityCoefficient);

    BigDecimal getOverpaymentAmount(List<MonthlyPayment> monthlyPayments, List<EarlyPayment> earlyPayments);

    BigDecimal getBodyAmountRemainder(Schedule schedule, LocalDate earlyPaymentDate);

    BigDecimal getPercentAmountUntilEndOfMonth(BigDecimal dailyPercentAmount, LocalDate earlyPaymentDate);

}

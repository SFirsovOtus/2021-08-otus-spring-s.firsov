package ru.otus.spring.credit.calculator.service.calculation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.credit.calculator.domain.MonthlyPayment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;

import static ru.otus.spring.credit.calculator.service.calculation.AmountCalculationServiceImpl.PAYMENT_AMOUNT_PRECISION;

@Service
@RequiredArgsConstructor
public class MonthlyPaymentCalculationServiceImpl implements MonthlyPaymentCalculationService {

    private final AmountCalculationService amountCalculationService;
    private final DateCalculationService dateCalculationService;


    @Override
    public MonthlyPayment getMonthlyPaymentForFullMonth(YearMonth yearMonth,
                                                        BigDecimal bodyAmountRemainder,
                                                        BigDecimal yearPercent,
                                                        BigDecimal monthlyPaymentAmount) {

        LocalDate lastDateOfMonth = dateCalculationService.getLastDateOfMonth(yearMonth);
        int daysNumberInMonth = dateCalculationService.getDaysNumberInMonth(yearMonth);
        BigDecimal dailyPercentAmount = amountCalculationService.getDailyPercentAmount(bodyAmountRemainder, yearPercent, lastDateOfMonth);

        BigDecimal percentAmount = dailyPercentAmount.multiply(BigDecimal.valueOf(daysNumberInMonth))
                .setScale(PAYMENT_AMOUNT_PRECISION, RoundingMode.HALF_UP);
        BigDecimal bodyAmount = monthlyPaymentAmount.subtract(percentAmount).min(bodyAmountRemainder);

        return new MonthlyPayment(lastDateOfMonth, bodyAmount, percentAmount);
    }

    @Override
    public MonthlyPayment getMonthlyPaymentForPartOfMonth(LocalDate earlyPaymentDate,
                                                          BigDecimal bodyAmountRemainder,
                                                          BigDecimal yearPercent,
                                                          BigDecimal monthlyPaymentAmount) {

        YearMonth yearMonth = YearMonth.of(earlyPaymentDate.getYear(), earlyPaymentDate.getMonthValue());
        LocalDate lastDateOfMonth = dateCalculationService.getLastDateOfMonth(yearMonth);
        int daysNumberUntilEndOfMonth = dateCalculationService.getDaysNumberUntilEndOfMonth(earlyPaymentDate);
        BigDecimal dailyPercentAmount = amountCalculationService.getDailyPercentAmount(bodyAmountRemainder, yearPercent, lastDateOfMonth);

        BigDecimal percentAmount = dailyPercentAmount.multiply(BigDecimal.valueOf(daysNumberUntilEndOfMonth))
                .setScale(PAYMENT_AMOUNT_PRECISION, RoundingMode.HALF_UP);
        BigDecimal bodyAmount = monthlyPaymentAmount.subtract(percentAmount).min(bodyAmountRemainder);

        return new MonthlyPayment(lastDateOfMonth, bodyAmount, percentAmount);
    }

}

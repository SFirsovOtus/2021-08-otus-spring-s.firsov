package ru.otus.spring.credit.calculator.service.calculation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import ru.otus.spring.credit.calculator.domain.EarlyPayment;
import ru.otus.spring.credit.calculator.domain.MonthlyPayment;
import ru.otus.spring.credit.calculator.domain.Schedule;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AmountCalculationServiceImpl implements AmountCalculationService {

    private final DateCalculationService dateCalculationService;
    private final PercentCalculationService percentCalculationService;


    public static final int COEFFICIENT_PRECISION = 10;
    public static final int PAYMENT_AMOUNT_PRECISION = 2;
    public static final int DAILY_AMOUNT_PRECISION = 10;

    @Override
    public BigDecimal getDailyPercentAmount(BigDecimal bodyAmount, BigDecimal yearPercent, LocalDate date) {
        return bodyAmount.multiply(percentCalculationService.getSharePercentForDate(yearPercent, date))
                .setScale(DAILY_AMOUNT_PRECISION, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal getAnnuityCoefficient(BigDecimal yearPercent, int monthsNumber) {
        BigDecimal sharePercentForMonth = percentCalculationService.getSharePercentForMonth(yearPercent);
        BigDecimal commonMemberOfNumeratorAndDenominator = BigDecimal.ONE.add(sharePercentForMonth).pow(monthsNumber);

        BigDecimal numerator = commonMemberOfNumeratorAndDenominator.multiply(sharePercentForMonth);
        BigDecimal denominator = commonMemberOfNumeratorAndDenominator.subtract(BigDecimal.ONE);

        return numerator.divide(denominator, COEFFICIENT_PRECISION, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal getMonthlyPaymentAmount(BigDecimal creditAmount, BigDecimal annuityCoefficient) {
        return creditAmount.multiply(annuityCoefficient)
                .setScale(PAYMENT_AMOUNT_PRECISION, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal getOverpaymentAmount(List<MonthlyPayment> monthlyPayments, List<EarlyPayment> earlyPayments) {
        BigDecimal overpaymentAmount = BigDecimal.ZERO;

        if (!ObjectUtils.isEmpty(monthlyPayments)) {
            overpaymentAmount = monthlyPayments.stream()
                    .map(MonthlyPayment::getPercentAmount)
                    .reduce(overpaymentAmount, BigDecimal::add);
        }

        if (!ObjectUtils.isEmpty(earlyPayments)) {
            overpaymentAmount = earlyPayments.stream()
                    .map(EarlyPayment::getPercentAmount)
                    .reduce(overpaymentAmount, BigDecimal::add);
        }

        return overpaymentAmount;
    }

    @Override
    public BigDecimal getBodyAmountRemainder(Schedule schedule, LocalDate earlyPaymentDate) {
        return schedule.getMonthlyPayments().stream()
                .filter(mp -> mp.getDate().compareTo(earlyPaymentDate) >= 0)
                .map(MonthlyPayment::getBodyAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal getPercentAmountUntilEndOfMonth(BigDecimal dailyPercentAmount, LocalDate earlyPaymentDate) {
        int daysNumberUntilEndOfMonth = dateCalculationService.getDaysNumberUntilEndOfMonth(earlyPaymentDate);

        return dailyPercentAmount.multiply(BigDecimal.valueOf(daysNumberUntilEndOfMonth))
                .setScale(PAYMENT_AMOUNT_PRECISION, RoundingMode.HALF_UP);
    }

}

package ru.otus.spring.credit.calculator.service.calculation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.credit.calculator.domain.EarlyPayment;
import ru.otus.spring.credit.calculator.domain.Schedule;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;

import static ru.otus.spring.credit.calculator.service.calculation.AmountCalculationServiceImpl.PAYMENT_AMOUNT_PRECISION;

@Service
@RequiredArgsConstructor
public class EarlyPaymentCalculationServiceImpl implements EarlyPaymentCalculationService {

    private final AmountCalculationService amountCalculationService;
    private final DateCalculationService dateCalculationService;


    @Override
    public EarlyPayment getEarlyPayment(Schedule lastSchedule, EarlyPayment earlyPayment, BigDecimal yearPercent) {
        LocalDate previousEarlyBodyPaymentDate = lastSchedule.getEarlyPayments().stream()
                .filter(ep -> BigDecimal.ZERO.compareTo(ep.getBodyAmount()) < 0)
                .map(EarlyPayment::getDate)
                .filter(epd -> epd.withDayOfMonth(1).equals(earlyPayment.getDate().withDayOfMonth(1)))
                .max(Comparator.naturalOrder())
                .orElse(earlyPayment.getDate().withDayOfMonth(1).minusDays(1));

        int daysNumberForPercentAmountAccruing = dateCalculationService.getDaysBetweenTwoDates(previousEarlyBodyPaymentDate, earlyPayment.getDate());
        BigDecimal bodyAmountRemainder = amountCalculationService.getBodyAmountRemainder(lastSchedule, earlyPayment.getDate());
        BigDecimal dailyPercentAmount = amountCalculationService.getDailyPercentAmount(bodyAmountRemainder, yearPercent, earlyPayment.getDate());

        BigDecimal repaidAccruedPercentAmount = lastSchedule.getEarlyPayments().stream()
                .filter(ep -> ep.getDate().isAfter(previousEarlyBodyPaymentDate))
                .map(EarlyPayment::getPercentAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal remainingAccruedPercentAmount = BigDecimal.valueOf(daysNumberForPercentAmountAccruing)
                .multiply(dailyPercentAmount)
                .subtract(repaidAccruedPercentAmount)
                .setScale(PAYMENT_AMOUNT_PRECISION, RoundingMode.HALF_UP);

        BigDecimal earlyPaymentAmount = earlyPayment.getAmount();

        BigDecimal repaidPercentAmount = remainingAccruedPercentAmount.min(earlyPaymentAmount);
        earlyPaymentAmount = earlyPaymentAmount.subtract(repaidPercentAmount);

        BigDecimal repaidBodyAmount = bodyAmountRemainder.min(earlyPaymentAmount);

        return new EarlyPayment(earlyPayment.getDate(), earlyPayment.getAmount(), earlyPayment.getType(), repaidBodyAmount, repaidPercentAmount);
    }

}

package ru.otus.spring.credit.calculator.service.calculation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import ru.otus.spring.credit.calculator.domain.CreditParameters;
import ru.otus.spring.credit.calculator.domain.EarlyPayment;
import ru.otus.spring.credit.calculator.domain.MonthlyPayment;
import ru.otus.spring.credit.calculator.domain.Schedule;
import ru.otus.spring.credit.calculator.enums.EarlyRepaymentType;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleCalculationServiceImpl implements ScheduleCalculationService {

    private final AmountCalculationService amountCalculationService;
    private final EarlyPaymentCalculationService earlyPaymentCalculationService;
    private final MonthlyPaymentCalculationService monthlyPaymentCalculationService;


    @Override
    public Schedule getOriginalSchedule(CreditParameters creditParameters) {
        BigDecimal annuityCoefficient = amountCalculationService.getAnnuityCoefficient(
                creditParameters.getYearPercent(), creditParameters.getDurationInMonths());
        BigDecimal monthlyPaymentAmount = amountCalculationService.getMonthlyPaymentAmount(
                creditParameters.getCreditAmount(), annuityCoefficient);

        BigDecimal bodyAmountRemainder  = creditParameters.getCreditAmount();
        YearMonth yearMonth = creditParameters.getStartMonth();

        List<MonthlyPayment> monthlyPayments = new ArrayList<>();
        for (int i = 1; i <= creditParameters.getDurationInMonths(); i++) {
            MonthlyPayment monthlyPayment = monthlyPaymentCalculationService.getMonthlyPaymentForFullMonth(
                    yearMonth, bodyAmountRemainder, creditParameters.getYearPercent(), monthlyPaymentAmount);

            monthlyPayments.add(monthlyPayment);

            bodyAmountRemainder = bodyAmountRemainder.subtract(monthlyPayment.getBodyAmount());
            if (BigDecimal.ZERO.compareTo(bodyAmountRemainder) == 0) {
                break;
            }
            yearMonth = yearMonth.plusMonths(1);
        }

        BigDecimal overpaymentAmount = amountCalculationService.getOverpaymentAmount(monthlyPayments, null);

        return new Schedule(Collections.emptyList(), monthlyPaymentAmount, monthlyPayments, overpaymentAmount);
    }

    @Override
    public Schedule getEarlySchedule(Schedule lastSchedule, EarlyPayment earlyPayment, BigDecimal yearPercent) {
        EarlyPayment totalEarlyPayment = earlyPaymentCalculationService.getEarlyPayment(lastSchedule, earlyPayment, yearPercent);

        BigDecimal bodyAmountRemainder = amountCalculationService.getBodyAmountRemainder(lastSchedule, totalEarlyPayment.getDate())
                .subtract(totalEarlyPayment.getBodyAmount());

        if (BigDecimal.ZERO.compareTo(bodyAmountRemainder) == 0) {
            return getFinalSchedule(lastSchedule, totalEarlyPayment);
        }

        if (BigDecimal.ZERO.compareTo(totalEarlyPayment.getBodyAmount()) == 0) {
            return getScheduleWithRepaidPercentAmountOnly(lastSchedule, totalEarlyPayment);
        }

        return getScheduleWithRepaidBodyAmount(lastSchedule, yearPercent, bodyAmountRemainder, totalEarlyPayment);
    }



    private Schedule getFinalSchedule(Schedule lastSchedule, EarlyPayment totalEarlyPayment) {
        List<EarlyPayment> earlyPayments = new ArrayList<>();
        if (!ObjectUtils.isEmpty(lastSchedule.getEarlyPayments())) {
            earlyPayments.addAll(lastSchedule.getEarlyPayments());
        }
        earlyPayments.add(totalEarlyPayment);

        List<MonthlyPayment> monthlyPayments = lastSchedule.getMonthlyPayments().stream()
                .filter(mp -> mp.getDate().isBefore(totalEarlyPayment.getDate()))
                .collect(Collectors.toList());

        BigDecimal overpaymentAmount = amountCalculationService.getOverpaymentAmount(monthlyPayments, earlyPayments);

        return new Schedule(earlyPayments, BigDecimal.ZERO, monthlyPayments, overpaymentAmount);
    }

    private Schedule getScheduleWithRepaidPercentAmountOnly(Schedule lastSchedule, EarlyPayment totalEarlyPayment) {
        List<EarlyPayment> earlyPayments = new ArrayList<>();
        if (!ObjectUtils.isEmpty(lastSchedule.getEarlyPayments())) {
            earlyPayments.addAll(lastSchedule.getEarlyPayments());
        }
        earlyPayments.add(totalEarlyPayment);

        List<MonthlyPayment> monthlyPayments = lastSchedule.getMonthlyPayments();
        for (MonthlyPayment monthlyPayment : monthlyPayments) {
            if (monthlyPayment.getDate().compareTo(totalEarlyPayment.getDate()) >= 0) {
                monthlyPayment.setPercentAmount(
                        monthlyPayment.getPercentAmount().subtract(totalEarlyPayment.getPercentAmount()).max(BigDecimal.ZERO));
                break;
            }
        }

        BigDecimal overpaymentAmount = amountCalculationService.getOverpaymentAmount(monthlyPayments, earlyPayments);

        return new Schedule(earlyPayments, lastSchedule.getMonthlyPaymentAmount(), monthlyPayments, overpaymentAmount);
    }

    private BigDecimal getMonthlyPaymentAmount(Schedule lastSchedule,
                                               BigDecimal yearPercent,
                                               BigDecimal bodyAmountRemainder,
                                               EarlyPayment totalEarlyPayment) {

        if (EarlyRepaymentType.DURATION_REDUCTION.equals(totalEarlyPayment.getType())) {
            return lastSchedule.getMonthlyPaymentAmount();
        } else {
            int monthsNumber = (int) lastSchedule.getMonthlyPayments().stream()
                    .filter(mp -> mp.getDate().compareTo(totalEarlyPayment.getDate()) >= 0)
                    .count();
            BigDecimal annuityCoefficient = amountCalculationService.getAnnuityCoefficient(yearPercent, monthsNumber);
            return amountCalculationService.getMonthlyPaymentAmount(bodyAmountRemainder, annuityCoefficient);
        }
    }

    private Schedule getScheduleWithRepaidBodyAmount(Schedule lastSchedule,
                                                     BigDecimal yearPercent,
                                                     BigDecimal bodyAmountRemainder,
                                                     EarlyPayment totalEarlyPayment) {

        BigDecimal monthlyPaymentAmount = getMonthlyPaymentAmount(lastSchedule, yearPercent, bodyAmountRemainder, totalEarlyPayment);

        List<MonthlyPayment> futureMonthlyPayments = new ArrayList<>();

        MonthlyPayment nearestMonthlyPayment = monthlyPaymentCalculationService.getMonthlyPaymentForPartOfMonth(
                totalEarlyPayment.getDate(), bodyAmountRemainder, yearPercent, monthlyPaymentAmount);
        futureMonthlyPayments.add(nearestMonthlyPayment);

        bodyAmountRemainder = bodyAmountRemainder.subtract(nearestMonthlyPayment.getBodyAmount());
        YearMonth yearMonth = YearMonth.of(totalEarlyPayment.getDate().getYear(), totalEarlyPayment.getDate().getMonthValue())
                .plusMonths(1);

        while (BigDecimal.ZERO.compareTo(bodyAmountRemainder) < 0) {
            MonthlyPayment monthlyPayment = monthlyPaymentCalculationService.getMonthlyPaymentForFullMonth(
                    yearMonth, bodyAmountRemainder, yearPercent, monthlyPaymentAmount);

            futureMonthlyPayments.add(monthlyPayment);

            yearMonth = yearMonth.plusMonths(1);
            bodyAmountRemainder = bodyAmountRemainder.subtract(monthlyPayment.getBodyAmount());
        }

        List<MonthlyPayment> monthlyPayments = lastSchedule.getMonthlyPayments().stream()
                .filter(mp -> mp.getDate().isBefore(totalEarlyPayment.getDate()))
                .collect(Collectors.toList());
        monthlyPayments.addAll(futureMonthlyPayments);

        List<EarlyPayment> earlyPayments = new ArrayList<>();
        if (!ObjectUtils.isEmpty(lastSchedule.getEarlyPayments())) {
            earlyPayments.addAll(lastSchedule.getEarlyPayments());
        }
        earlyPayments.add(totalEarlyPayment);

        BigDecimal overpaymentAmount = amountCalculationService.getOverpaymentAmount(monthlyPayments, earlyPayments);

        return new Schedule(earlyPayments, monthlyPaymentAmount, monthlyPayments, overpaymentAmount);
    }

}

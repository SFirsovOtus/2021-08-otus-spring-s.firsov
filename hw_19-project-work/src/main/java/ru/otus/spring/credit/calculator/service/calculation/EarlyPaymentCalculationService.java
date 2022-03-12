package ru.otus.spring.credit.calculator.service.calculation;

import ru.otus.spring.credit.calculator.domain.EarlyPayment;
import ru.otus.spring.credit.calculator.domain.Schedule;

import java.math.BigDecimal;

public interface EarlyPaymentCalculationService {

    EarlyPayment getEarlyPayment(Schedule lastSchedule, EarlyPayment earlyPayment, BigDecimal yearPercent);

}

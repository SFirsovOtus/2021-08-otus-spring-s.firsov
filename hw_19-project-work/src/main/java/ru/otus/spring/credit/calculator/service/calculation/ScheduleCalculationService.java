package ru.otus.spring.credit.calculator.service.calculation;

import ru.otus.spring.credit.calculator.domain.CreditParameters;
import ru.otus.spring.credit.calculator.domain.EarlyPayment;
import ru.otus.spring.credit.calculator.domain.Schedule;

import java.math.BigDecimal;

public interface ScheduleCalculationService {

    Schedule getOriginalSchedule(CreditParameters creditParameters);

    Schedule getEarlySchedule(Schedule lastSchedule, EarlyPayment earlyPayment, BigDecimal yearPercent);

}

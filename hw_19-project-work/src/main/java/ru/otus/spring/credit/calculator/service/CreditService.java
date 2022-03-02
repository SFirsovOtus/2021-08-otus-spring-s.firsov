package ru.otus.spring.credit.calculator.service;

import ru.otus.spring.credit.calculator.domain.Credit;
import ru.otus.spring.credit.calculator.domain.ScheduleChain;

import java.util.List;

public interface CreditService {

    List<Credit> findAll(String username);

    void deleteById(String id);

    ScheduleChain save(ScheduleChain scheduleChain);

}

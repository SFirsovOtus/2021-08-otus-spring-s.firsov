package ru.otus.spring.credit.calculator.service;

import ru.otus.spring.credit.calculator.domain.ScheduleChain;

import java.util.Optional;

public interface ScheduleChainService {

    Optional<ScheduleChain> findById(String id);

}

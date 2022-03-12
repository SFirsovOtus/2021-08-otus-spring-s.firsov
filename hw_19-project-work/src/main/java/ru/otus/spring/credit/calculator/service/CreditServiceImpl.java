package ru.otus.spring.credit.calculator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.credit.calculator.domain.Credit;
import ru.otus.spring.credit.calculator.domain.ScheduleChain;
import ru.otus.spring.credit.calculator.repository.CreditRepository;
import ru.otus.spring.credit.calculator.repository.ScheduleChainRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {

    private final CreditRepository creditRepository;
    private final ScheduleChainRepository scheduleChainRepository;


    @Override
    public List<Credit> findAll(String username) {
        return creditRepository.findAll(username);
    }

    @Override
    public void deleteById(String id) {
        creditRepository.deleteById(id);
    }

    @Override
    public ScheduleChain save(ScheduleChain scheduleChain) {
        return scheduleChainRepository.save(scheduleChain);
    }

}

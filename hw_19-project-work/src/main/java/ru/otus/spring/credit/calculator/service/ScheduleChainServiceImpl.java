package ru.otus.spring.credit.calculator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.credit.calculator.domain.ScheduleChain;
import ru.otus.spring.credit.calculator.repository.ScheduleChainRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleChainServiceImpl implements ScheduleChainService {

    private final ScheduleChainRepository scheduleChainRepository;


    @Override
    public Optional<ScheduleChain> findById(String id) {
        return scheduleChainRepository.findById(id);
    }

}

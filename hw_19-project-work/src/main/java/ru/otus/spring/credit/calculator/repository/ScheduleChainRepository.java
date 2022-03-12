package ru.otus.spring.credit.calculator.repository;

import com.mongodb.lang.NonNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.credit.calculator.domain.ScheduleChain;

import java.util.Optional;

public interface ScheduleChainRepository extends MongoRepository<ScheduleChain, String> {

    Optional<ScheduleChain> findById(@NonNull String id);

}

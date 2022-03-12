package ru.otus.spring.credit.calculator.repository;

import com.mongodb.lang.NonNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.spring.credit.calculator.domain.Credit;
import ru.otus.spring.credit.calculator.domain.ScheduleChain;

import java.util.List;

public interface CreditRepository extends MongoRepository<ScheduleChain, String> {

    @Query(value = "{'username' : :#{#username}}", fields = "{_id : 1, creditParameters : 1}")
    List<Credit> findAll(@Param("username") String username);

    void deleteById(@NonNull String id);

}

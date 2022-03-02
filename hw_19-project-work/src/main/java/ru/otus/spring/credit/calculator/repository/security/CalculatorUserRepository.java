package ru.otus.spring.credit.calculator.repository.security;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.credit.calculator.domain.security.CalculatorUser;

import java.util.Optional;

public interface CalculatorUserRepository extends MongoRepository<CalculatorUser, String> {

    Optional<CalculatorUser> findByUsername(String username);

}

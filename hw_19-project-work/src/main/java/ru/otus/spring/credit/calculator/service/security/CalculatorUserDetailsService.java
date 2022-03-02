package ru.otus.spring.credit.calculator.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.spring.credit.calculator.domain.security.CalculatorUser;
import ru.otus.spring.credit.calculator.repository.security.CalculatorUserRepository;

@Service
@RequiredArgsConstructor
public class CalculatorUserDetailsService implements UserDetailsService {

    private final CalculatorUserRepository calculatorUserRepository;


    public static final String ROLE_COMMON = "COMMON";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CalculatorUser calculatorUser = calculatorUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return User.withUsername(calculatorUser.getUsername())
                .password(calculatorUser.getPassword())
                .roles(ROLE_COMMON)
                .build();
    }

}

package ru.otus.spring.quiz.service;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.otus.spring.quiz.config.LocaleConfig;

@Service
@Primary
@AllArgsConstructor
public class LocalizationServiceImpl implements LocalizationService {

    private final LocaleConfig localeConfig;
    private final MessageSource messageSource;


    @Override
    public String getMessage(String property, String ... args) {
        return messageSource.getMessage(property, args, localeConfig.getLocale());
    }

}

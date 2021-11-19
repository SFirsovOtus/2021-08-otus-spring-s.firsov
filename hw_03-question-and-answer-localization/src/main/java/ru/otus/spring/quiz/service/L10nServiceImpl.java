package ru.otus.spring.quiz.service;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.quiz.config.LocaleConfig;

@Service
@AllArgsConstructor
public class L10nServiceImpl implements L10nService {

    private final LocaleConfig localeConfig;
    private final MessageSource messageSource;


    @Override
    public String getMessage(String property, String ... args) {
        return messageSource.getMessage(property, args, localeConfig.getLocale());
    }

}

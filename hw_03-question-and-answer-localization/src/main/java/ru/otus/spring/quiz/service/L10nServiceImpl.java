package ru.otus.spring.quiz.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class L10nServiceImpl implements L10nService {

    private final Locale locale;
    private final MessageSource messageSource;

    private static final String ENGLISH_LOCALE = "en-EN";

    public L10nServiceImpl(@Value("${quiz.locale}") String locale, MessageSource messageSource) {
        this.locale = Locale.forLanguageTag(ENGLISH_LOCALE.equals(locale) ? "" : locale);
        this.messageSource = messageSource;
    }


    @Override
    public String getMessage(String property, String ... args) {
        return messageSource.getMessage(property, args, locale);
    }

}

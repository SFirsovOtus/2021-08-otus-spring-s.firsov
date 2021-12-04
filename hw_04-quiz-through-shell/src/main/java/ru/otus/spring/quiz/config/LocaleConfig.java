package ru.otus.spring.quiz.config;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Getter
@Configuration
public class LocaleConfig {

    private final Locale locale;
    private final String localeSuffix;

    public LocaleConfig(@Value("${quiz.locale:}") String locale) {
        this.locale = Locale.forLanguageTag(locale);

        String localeTag = this.locale.toString();
        this.localeSuffix = StringUtils.EMPTY.equals(localeTag) ? localeTag : String.format("_%s", localeTag);
    }

}

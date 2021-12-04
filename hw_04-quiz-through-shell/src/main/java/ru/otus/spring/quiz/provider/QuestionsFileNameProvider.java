package ru.otus.spring.quiz.provider;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.spring.quiz.config.LocaleConfig;

@Getter
@Component
public class QuestionsFileNameProvider {

    private final String resourcePath;
    private final String resourcePathDefault;

    public QuestionsFileNameProvider(@Value("${resources.csv-data-storage.path}") String resourcePath,
                                     LocaleConfig localeConfig) {
        this.resourcePath = String.format(resourcePath, localeConfig.getLocaleSuffix());
        this.resourcePathDefault = String.format(resourcePath, StringUtils.EMPTY);
    }

}

package ru.otus.spring.book.library.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerConfig {

    private static final String LOGGER_NAME = "BookMigrationLogger";

    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger(LOGGER_NAME);
    }

}

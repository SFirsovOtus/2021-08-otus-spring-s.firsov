package ru.otus.spring.book.library.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties("spring.data.mongodb")
public class MongoProperties {

    private String host;

    private Integer port;

    private String database;

}

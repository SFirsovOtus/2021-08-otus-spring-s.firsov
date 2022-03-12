package ru.otus.spring.credit.calculator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import ru.otus.spring.credit.calculator.converter.*;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongoConverterConfig {

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new LocalDateToStringConverter());
        converters.add(new YearMonthToStringConverter());
        converters.add(new StringToYearMonthConverter());

        return new MongoCustomConversions(converters);
    }

}

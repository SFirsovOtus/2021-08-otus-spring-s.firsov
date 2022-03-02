package ru.otus.spring.credit.calculator.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.YearMonth;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Document
public class CreditParameters {

    private BigDecimal creditAmount;

    private BigDecimal yearPercent;

    private YearMonth startMonth;

    private Integer durationInMonths;

}

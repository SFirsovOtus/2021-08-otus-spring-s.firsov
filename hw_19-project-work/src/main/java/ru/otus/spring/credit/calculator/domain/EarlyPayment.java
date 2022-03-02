package ru.otus.spring.credit.calculator.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.otus.spring.credit.calculator.enums.EarlyRepaymentType;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Document
public class EarlyPayment {

    private LocalDate date;

    private BigDecimal amount;

    private EarlyRepaymentType type;

    private BigDecimal bodyAmount;

    private BigDecimal percentAmount;


    public EarlyPayment(LocalDate date, BigDecimal amount, EarlyRepaymentType type) {
        this.date = date;
        this.amount = amount;
        this.type = type;
    }

}

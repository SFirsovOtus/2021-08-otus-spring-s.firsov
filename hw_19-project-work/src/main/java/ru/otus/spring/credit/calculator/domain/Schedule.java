package ru.otus.spring.credit.calculator.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Document
public class Schedule {

    // для исходного графика это будет пустой список
    private List<EarlyPayment> earlyPayments;

    private BigDecimal monthlyPaymentAmount;

    private List<MonthlyPayment> monthlyPayments;

    private BigDecimal overpaymentAmount;

}

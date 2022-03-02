package ru.otus.spring.credit.calculator.enums;

import java.util.HashMap;
import java.util.Map;

public enum EarlyRepaymentType {

    DURATION_REDUCTION,

    PAYMENT_REDUCTION;


    public static final Map<String, EarlyRepaymentType> EARLY_REPAYMENT_TYPES = getEarlyRepaymentTypes();

    private static Map<String, EarlyRepaymentType> getEarlyRepaymentTypes() {
        Map<String, EarlyRepaymentType> earlyRepaymentTypes = new HashMap<>();

        for (EarlyRepaymentType ert : values()) {
            earlyRepaymentTypes.put(ert.name(), ert);
        }

        return earlyRepaymentTypes;
    }

}

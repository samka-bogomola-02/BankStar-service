package bank.recommendationservice.fintech.util;

import bank.recommendationservice.fintech.exception.NoTransactionsFoundException;

public class RuleUtil {
    public static void validateNotNull(Object value) {
        if (value == null) {
            throw new NoTransactionsFoundException("Из базы данных получено значение null");
        }
    }
}
package bank.recommendationservice.fintech.other;

import bank.recommendationservice.fintech.exception.UnknownComprasionTypeException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ComparisonType {
    GREATER_THAN(">"),

    LESS_THAN("<"),

    EQUALS("="),

    GREATER_THAN_OR_EQUALS(">="),

    LESS_THAN_OR_EQUALS("<=");


    private final String comparisonType;

    ComparisonType(String comparisonType) {
        this.comparisonType = comparisonType;

    }

    public static boolean isValidComparison(String comparisonType) {
        return Arrays.stream(ComparisonType.values())
                .anyMatch(c -> c.getComparisonType().equals(comparisonType));
    }

    public static ComparisonType fromString(String comparisonType) {
        for (ComparisonType type : ComparisonType.values()) {
            if (type.getComparisonType().equals(comparisonType)) {
                return type;
            }
        }
        throw new UnknownComprasionTypeException("Неизвестный тип сравнения: " + comparisonType);
    }
}



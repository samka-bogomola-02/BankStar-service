package bank.recommendationservice.fintech.other;

import bank.recommendationservice.fintech.exception.UnknownComparisonTypeException;
import lombok.Getter;

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

    public static ComparisonType fromString(String comparisonType) {
        for (ComparisonType type : ComparisonType.values()) {
            if (type.getComparisonType().equals(comparisonType)) {
                return type;
            }
        }
        throw new UnknownComparisonTypeException("Неизвестный тип сравнения: " + comparisonType);
    }
}



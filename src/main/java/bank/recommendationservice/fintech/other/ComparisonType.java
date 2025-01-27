package bank.recommendationservice.fintech.other;

public enum ComparisonType {
    GREATER_THAN(">"),

    LESS_THAN("<"),

    EQUAL("="),

    GREATER_THAN_OR_EQUAL(">="),

    LESS_THAN_OR_EQUAL("<=");

    ComparisonType(String type) {
    }
}
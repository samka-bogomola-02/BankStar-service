package bank.recommendationservice.fintech.other;

public enum ComparisonType {
    GREATER_THAN(">"),

    LESS_THAN("<"),

    EQUALS("="),

    GREATER_THAN_OR_EQUALS(">="),

    LESS_THAN_OR_EQUALS("<=");

    ComparisonType(String type) {
    }
}

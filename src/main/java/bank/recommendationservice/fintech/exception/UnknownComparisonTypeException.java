package bank.recommendationservice.fintech.exception;

public class UnknownComparisonTypeException extends RuntimeException {

    public UnknownComparisonTypeException() {
    }

    public UnknownComparisonTypeException(String s) {
        super(s);
    }
}

package bank.recommendationservice.fintech.exception;

public class UnknownQueryTypeException extends IllegalArgumentException {
    public UnknownQueryTypeException() {
    }

    public UnknownQueryTypeException(String s) {
        super(s);
    }
}

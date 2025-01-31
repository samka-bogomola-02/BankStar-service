package bank.recommendationservice.fintech.exception;

public class UnknownCompressionTypeException extends RuntimeException {

    public UnknownCompressionTypeException() {
    }

    public UnknownCompressionTypeException(String s) {
        super(s);
    }
}

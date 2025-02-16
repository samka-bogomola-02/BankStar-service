package bank.recommendationservice.fintech.exception;

public class RulesNotFoundException extends RuntimeException {
    public RulesNotFoundException(String message) {
        super(message);
    }
}

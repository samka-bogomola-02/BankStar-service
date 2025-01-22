package bank.recommendationservice.fintech.exception;

public class NoTransactionsFoundException extends RuntimeException {
    public NoTransactionsFoundException() {
    }

    public NoTransactionsFoundException(String message) {
        super(message);
    }
}

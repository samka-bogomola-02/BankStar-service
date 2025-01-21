package bank.recommendationservice.fintech.exception;

public class NullResultFromRepositoryException extends RuntimeException {
    public NullResultFromRepositoryException() {
    }

    public NullResultFromRepositoryException(String message) {
        super(message);
    }
}

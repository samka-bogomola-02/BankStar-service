package bank.recommendationservice.fintech.exception;

public class NullArgumentException extends IllegalArgumentException{
    public NullArgumentException() {
    }

    public NullArgumentException(String message) {
        super(message);
    }
}

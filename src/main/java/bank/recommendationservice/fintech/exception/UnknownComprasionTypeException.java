package bank.recommendationservice.fintech.exception;

public class UnknownComprasionTypeException extends IllegalArgumentException {

    public UnknownComprasionTypeException() {
    }

    public UnknownComprasionTypeException(String s) {
        super(s);
    }
}

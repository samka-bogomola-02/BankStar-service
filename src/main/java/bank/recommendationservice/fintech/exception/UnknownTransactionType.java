package bank.recommendationservice.fintech.exception;

public class UnknownTransactionType extends IllegalArgumentException {
    public UnknownTransactionType() {

    }

    public UnknownTransactionType(String s) {
        super(s);
    }

}

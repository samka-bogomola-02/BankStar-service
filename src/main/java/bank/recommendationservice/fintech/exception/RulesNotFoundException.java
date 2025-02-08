package bank.recommendationservice.fintech.exception;

public class RulesNotFoundException extends BaseNotFoundException {
private final Long ruleId;

    public RulesNotFoundException(String message, Long ruleId) {
        super(message);
        this.ruleId = ruleId;
    }

    public Long getRuleId() {
        return ruleId;
    }
}

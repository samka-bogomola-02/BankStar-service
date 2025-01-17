package bank.recommendationservice.fintech.rule;

import java.util.UUID;

public interface Rule {
    boolean evaluate(UUID userId);
}

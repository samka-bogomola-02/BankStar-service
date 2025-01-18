package bank.recommendationservice.fintech.rule.impl;

import bank.recommendationservice.fintech.exception.NoTransactionsFoundException;
import bank.recommendationservice.fintech.repository.RecommendationsRepository;
import bank.recommendationservice.fintech.rule.ProductType;
import bank.recommendationservice.fintech.rule.Rule;
import bank.recommendationservice.fintech.rule.util.RuleUtil;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Правило: сумма трат по всем продуктам типа DEBIT должно быть больше 100 000
 */
@Component
public class DebitWithdrawsTotalGreaterThan100_000 implements Rule {
    private final RecommendationsRepository recommendationsRepository;

    public DebitWithdrawsTotalGreaterThan100_000(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    @Override
    public boolean evaluate(UUID userId) {
        Integer debitWithdrawsTotal = recommendationsRepository.getWithdrawsOfTypeTotal(userId, ProductType.DEBIT.name());
        RuleUtil.validateNotNull(debitWithdrawsTotal);
        int threshold = 100_000;
        return debitWithdrawsTotal > threshold;
    }
}

package bank.recommendationservice.fintech.rule.impl;

import bank.recommendationservice.fintech.exception.NoTransactionsFoundException;
import bank.recommendationservice.fintech.repository.RecommendationsRepository;
import bank.recommendationservice.fintech.rule.ProductType;
import bank.recommendationservice.fintech.rule.Rule;
import bank.recommendationservice.fintech.rule.util.RuleUtil;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Правило:
 * Сумма пополнений продуктов с типом SAVING больше 1000 ₽
 */
@Component
public class SavingDepositsTotalGreaterThan1_000 implements Rule {
    private final RecommendationsRepository recommendationsRepository;

    public SavingDepositsTotalGreaterThan1_000(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    @Override
    public boolean evaluate(UUID userId) {
        Integer total = recommendationsRepository.getDepositsOfTypeTotal(userId, ProductType.SAVING.name());
        RuleUtil.validateNotNull(total);
        int threshold = 1000;
        return total > threshold;
    }

}

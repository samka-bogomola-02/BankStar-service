package bank.recommendationservice.fintech.rule.impl;

import bank.recommendationservice.fintech.repository.RecommendationsRepository;
import bank.recommendationservice.fintech.rule.ProductType;
import bank.recommendationservice.fintech.rule.Rule;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Правило:
 * Пользователь не использует продукты с типом CREDIT
 */
@Component
public class UsesNoCreditProducts implements Rule {
    private final RecommendationsRepository recommendationsRepository;

    public UsesNoCreditProducts(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    @Override
    public boolean evaluate(UUID userId) {
        return !recommendationsRepository.usesProductOfType(userId, ProductType.CREDIT.name());
    }
}

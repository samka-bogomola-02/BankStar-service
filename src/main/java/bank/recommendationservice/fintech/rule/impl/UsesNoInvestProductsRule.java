package bank.recommendationservice.fintech.rule.impl;

import bank.recommendationservice.fintech.repository.RecommendationsRepository;
import bank.recommendationservice.fintech.rule.Rule;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UsesNoInvestProductsRule implements Rule {
    private final RecommendationsRepository recommendationsRepository;

    public UsesNoInvestProductsRule(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    @Override
    public boolean evaluate(UUID userId) {
        return recommendationsRepository.userHasNoInvestProducts(userId);
    }

}

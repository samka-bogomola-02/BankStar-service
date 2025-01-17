package bank.recommendationservice.fintech.rule.impl;

import bank.recommendationservice.fintech.repository.RecommendationsRepository;
import bank.recommendationservice.fintech.rule.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UsesAtLeastOneDebitProductRule implements Rule {
    private final RecommendationsRepository recommendationsRepository;

    public UsesAtLeastOneDebitProductRule(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    @Override
    public boolean evaluate(UUID userId) {
        return recommendationsRepository.userHasAtLeastOneDebitProduct(userId);
    }
}

package bank.recommendationservice.fintech.rule;

import bank.recommendationservice.fintech.dto.RecommendationDTO;

import java.util.UUID;

public interface RecommendationRuleSet {
    RecommendationDTO recommend(UUID userId);
}

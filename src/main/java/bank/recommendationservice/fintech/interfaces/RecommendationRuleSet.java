package bank.recommendationservice.fintech.interfaces;

import bank.recommendationservice.fintech.dto.RecommendationDTO;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationRuleSet {
    Optional<RecommendationDTO> getRecommendation(UUID userId);
}

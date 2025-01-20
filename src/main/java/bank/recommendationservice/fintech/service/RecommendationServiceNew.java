package bank.recommendationservice.fintech.service;

import bank.recommendationservice.fintech.dto.RecommendationDTO;
import bank.recommendationservice.fintech.interfaces.RecommendationRuleSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RecommendationServiceNew {

    private final List<RecommendationRuleSet> recommendationRules;

    @Autowired
    public RecommendationServiceNew(List<RecommendationRuleSet> recommendationRules) {
        this.recommendationRules = recommendationRules;
    }

    public List<RecommendationDTO> getRecommendations(UUID userId) {
        List<RecommendationDTO> recommendations = new ArrayList<>();

        for (RecommendationRuleSet rule : recommendationRules) {
            rule.getRecommendation(userId).ifPresent(recommendations::add);
        }

        return recommendations;
    }
}


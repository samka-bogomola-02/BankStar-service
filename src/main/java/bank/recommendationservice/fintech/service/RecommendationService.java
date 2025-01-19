package bank.recommendationservice.fintech.service;


import bank.recommendationservice.fintech.dto.RecommendationDTO;
import bank.recommendationservice.fintech.rule.rulesetimpl.Invest500;
import bank.recommendationservice.fintech.rule.rulesetimpl.SimpleCredit;
import bank.recommendationservice.fintech.rule.rulesetimpl.TopSaving;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RecommendationService {
    private final Invest500 invest500;
    private final SimpleCredit simpleCredit;
    private final TopSaving topSaving;

    public RecommendationService(Invest500 invest500, SimpleCredit simpleCredit, TopSaving topSaving) {
        this.invest500 = invest500;
        this.simpleCredit = simpleCredit;
        this.topSaving = topSaving;
    }

    public List<RecommendationDTO> getRecommendations(UUID userId) {
        List<RecommendationDTO> result = new ArrayList<>();
        result.add(invest500.recommend(userId));
        result.add(simpleCredit.recommend(userId));
        result.add(topSaving.recommend(userId));
        return result;
    }
}

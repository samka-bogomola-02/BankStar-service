package bank.recommendationservice.fintech.service;

import bank.recommendationservice.fintech.interfaces.DynamicRuleRepository;
import bank.recommendationservice.fintech.model.RecommendationRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecommendationDynamicRuleService {

    @Autowired
    private DynamicRuleRepository DynamicRuleRepository;

    public RecommendationRule addRule(RecommendationRule rule) {
        return DynamicRuleRepository.save(rule);
    }
}


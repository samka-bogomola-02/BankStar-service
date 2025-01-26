package bank.recommendationservice.fintech.service;

import bank.recommendationservice.fintech.interfaces.DynamicRuleRepository;
import bank.recommendationservice.fintech.model.DynamicRule;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecommendationDynamicRuleService {

    @Autowired
    private DynamicRuleRepository DynamicRuleRepository;

    Logger logger = LoggerFactory.getLogger(RecommendationDynamicRuleService.class);

    @Transactional
    public DynamicRule addRule(DynamicRule rule) {
        logger.info(rule.toString());
        return DynamicRuleRepository.save(rule);
    }
}


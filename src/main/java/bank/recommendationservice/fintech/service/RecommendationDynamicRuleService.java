package bank.recommendationservice.fintech.service;

import bank.recommendationservice.fintech.exception.RulesNotFoundException;
import bank.recommendationservice.fintech.interfaces.DynamicRuleRepository;
import bank.recommendationservice.fintech.model.DynamicRule;
import bank.recommendationservice.fintech.model.DynamicRuleQuery;
import bank.recommendationservice.fintech.model.DynamicRuleQueryArgument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class RecommendationDynamicRuleService {
    @Autowired
    private DynamicRuleRepository dynamicRuleRepository;
    Logger logger = LoggerFactory.getLogger(RecommendationDynamicRuleService.class);


    @Transactional
    public DynamicRule addRule(DynamicRule rule) {
        logger.info("Добавление нового правила: {}", rule.toString());
        if (rule.getQueries() != null) {
            rule.getQueries().forEach(query -> {
                if (query.getArguments() != null) {
                    query.getArguments().forEach(argument -> argument.setDynamicRuleQuery(query));
                }
                query.setDynamicRule(rule);
            });
        }
        return dynamicRuleRepository.save(rule);
    }


    public DynamicRule deleteDynamicRule(Long id) {
        DynamicRule ruleToRemove = dynamicRuleRepository.findById(id)
                .orElseThrow(() -> new RulesNotFoundException("Правило не найдено!"));
        dynamicRuleRepository.delete(ruleToRemove);
        return ruleToRemove;
    }

    public List<DynamicRule> getAllDynamicRules() {
        return dynamicRuleRepository.findAll();
    }
}


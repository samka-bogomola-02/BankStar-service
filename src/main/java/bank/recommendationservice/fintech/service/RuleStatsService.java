package bank.recommendationservice.fintech.service;

import bank.recommendationservice.fintech.dto.RuleStatsDTO;
import bank.recommendationservice.fintech.exception.RulesNotFoundException;
import bank.recommendationservice.fintech.model.RuleStats;
import bank.recommendationservice.fintech.repository.DynamicRuleRepository;
import bank.recommendationservice.fintech.repository.RuleStatsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RuleStatsService {
    private final RuleStatsRepository ruleStatsRepository;
    private final DynamicRuleRepository dynamicRuleRepository;

    private final Logger logger = LoggerFactory.getLogger(RuleStatsService.class);

    RuleStatsService(RuleStatsRepository ruleStatsRepository,
                     DynamicRuleRepository dynamicRuleRepository) {
        this.ruleStatsRepository = ruleStatsRepository;
        this.dynamicRuleRepository = dynamicRuleRepository;
    }

    public void addRuleStats(Long dynamicRuleId) {
        RuleStats ruleStats = new RuleStats();
        ruleStats.setCount(0);
        ruleStats.setDynamicRule(dynamicRuleRepository.findById(dynamicRuleId).orElseThrow());
        ruleStatsRepository.save(ruleStats);
    }

    public List<RuleStatsDTO> getRuleStats() {
        return ruleStatsRepository.findAll().stream()
                .map(ruleStats -> new RuleStatsDTO(ruleStats.getDynamicRule().getId(), ruleStats.getCount()))
                .collect(Collectors.toList());
    }


public void increaseCounter(Long ruleId) {
    RuleStats ruleStats = ruleStatsRepository.findByDynamicRuleId(ruleId);

    if (ruleStats != null) {
        ruleStats.setCount(ruleStats.getCount() + 1);
        logger.debug("Увеличен счетчик срабатываний для правила с id: {}", ruleId);
        ruleStatsRepository.save(ruleStats);
    } else {
        logger.warn("Статистика срабатываний по правилу с id: {} не найлена", ruleId);
        throw new RulesNotFoundException("Статистика срабатываний по правилу не найдена");
    }
}
}

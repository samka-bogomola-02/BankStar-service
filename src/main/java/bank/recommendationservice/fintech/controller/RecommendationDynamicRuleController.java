package bank.recommendationservice.fintech.controller;

import bank.recommendationservice.fintech.model.DynamicRule;
import bank.recommendationservice.fintech.service.RecommendationDynamicRuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rule")
public class RecommendationDynamicRuleController {

    @Autowired
    private RecommendationDynamicRuleService recommendationDynamicRuleService;

    @PostMapping
    public ResponseEntity<DynamicRule> createRule(@RequestBody DynamicRule rule) {
        Logger logger = LoggerFactory.getLogger(RecommendationDynamicRuleService.class);
        logger.info("Добавление нового rule: {}", rule.toString());

        DynamicRule savedRule = recommendationDynamicRuleService.addRule(rule);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedRule);
    }

    @DeleteMapping
    public DynamicRule deleteRule(@RequestBody Long id) {
        return recommendationDynamicRuleService.deleteDynamicRule(id);
    }

    @GetMapping
    public List<DynamicRule> getAllRules() {
        return recommendationDynamicRuleService.getAllDynamicRules();
    }
}
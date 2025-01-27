package bank.recommendationservice.fintech.controller;

import bank.recommendationservice.fintech.model.DynamicRule;
import bank.recommendationservice.fintech.service.RecommendationDynamicRuleService;
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
        DynamicRule savedRule = recommendationDynamicRuleService.addRule(rule);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable Long id) {
        recommendationDynamicRuleService.deleteDynamicRule(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<DynamicRule>> getAllRules() {
        List<DynamicRule> rules = recommendationDynamicRuleService.getAllDynamicRules();
        return ResponseEntity.ok(rules);
    }
}
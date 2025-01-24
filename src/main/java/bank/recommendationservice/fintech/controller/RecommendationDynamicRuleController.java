package bank.recommendationservice.fintech.controller;

import bank.recommendationservice.fintech.model.RecommendationRule;
import bank.recommendationservice.fintech.service.RecommendationDynamicRuleService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rule")
public class RecommendationDynamicRuleController {

        @Autowired
        private RecommendationDynamicRuleService recommendationDynamicRuleService;

        @PostMapping
        public ResponseEntity<RecommendationRule> createRule(@RequestBody RecommendationRule newRule) {
            return ResponseEntity.ok(recommendationDynamicRuleService.addRule(newRule));
        }
}
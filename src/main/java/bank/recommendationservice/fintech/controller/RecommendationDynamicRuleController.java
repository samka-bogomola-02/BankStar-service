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

    /**
     * Создает новое динамическое правило.
     * <p>
     * Этот метод обрабатывает HTTP POST запросы для создания нового динамического правила.
     * Он принимает объект DynamicRule в качестве входных данных и сохраняет его с помощью
     * RecommendationDynamicRuleService. В случае успеха возвращает
     * ResponseEntity, содержащий сохраненное DynamicRule и статус
     * CREATED (201).
     *
     * @param rule DynamicRule, которое нужно создать
     * @return ResponseEntity, содержащий созданное DynamicRule и статус CREATED
     */

    @PostMapping
    public ResponseEntity<DynamicRule> createRule(@RequestBody DynamicRule rule) {
        DynamicRule savedRule = recommendationDynamicRuleService.addRule(rule);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRule);
    }

    /**
     * Удаляет существующее динамическое правило.
     * <p>
     * Этот метод обрабатывает HTTP DELETE запросы для удаления динамического правила
     * с указанным id. Он использует RecommendationDynamicRuleService для
     * удаления правила. Возвращает ResponseEntity с пустым телом
     * и статусом NO_CONTENT (204).
     *
     * @param id id динамического правила, которое нужно удалить
     * @return ResponseEntity с пустым телом и статусом NO_CONTENT
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable Long id) {
        recommendationDynamicRuleService.deleteDynamicRule(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Получает все динамические правила.
     * <p>
     * Этот метод обрабатывает HTTP GET запросы для получения всех динамических правил.
     * Он использует RecommendationDynamicRuleService для извлечения всех правил
     * из репозитория и возвращает их в виде списка. Возвращает ResponseEntity,
     * содержащий список DynamicRule и статус OK (200).
     *
     * @return ResponseEntity, содержащий список всех DynamicRule и статус OK
     */

    @GetMapping
    public ResponseEntity<List<DynamicRule>> getAllRules() {
        List<DynamicRule> rules = recommendationDynamicRuleService.getAllDynamicRules();
        return ResponseEntity.ok(rules);
    }
}
package bank.recommendationservice.fintech.interfaces;

import bank.recommendationservice.fintech.model.DynamicRule;
import bank.recommendationservice.fintech.model.RecommendationRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DynamicRuleRepository extends JpaRepository<RecommendationRule, Long> {
    // Сюда добавить каке-то методы поиска, при надобности
}

package bank.recommendationservice.fintech.repository;

import bank.recommendationservice.fintech.model.DynamicRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DynamicRuleRepository extends JpaRepository<DynamicRule, Long> {
    // Сюда добавить каке-то методы поиска, при надобности
}

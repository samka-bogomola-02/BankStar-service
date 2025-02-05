package bank.recommendationservice.fintech.repository;

import bank.recommendationservice.fintech.model.DynamicRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DynamicRuleRepository extends JpaRepository<DynamicRule, Long> {
    Optional<DynamicRule> findDynamicRuleById(Long id);
}

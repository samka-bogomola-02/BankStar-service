package bank.recommendationservice.fintech.repository;

import bank.recommendationservice.fintech.model.RuleStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleStatsRepository extends JpaRepository<RuleStats, Long> {
    RuleStats findByDynamicRuleId(Long ruleId);

    void deleteByDynamicRuleId(Long ruleId);
}

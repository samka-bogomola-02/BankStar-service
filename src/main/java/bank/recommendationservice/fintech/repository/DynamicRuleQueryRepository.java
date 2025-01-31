package bank.recommendationservice.fintech.repository;

import bank.recommendationservice.fintech.model.DynamicRuleQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DynamicRuleQueryRepository extends JpaRepository<DynamicRuleQuery, Long> {
    List<DynamicRuleQuery> findByDynamicRuleId(Long ruleId);
}

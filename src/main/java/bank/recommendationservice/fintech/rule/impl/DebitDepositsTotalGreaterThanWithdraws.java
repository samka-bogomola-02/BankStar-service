package bank.recommendationservice.fintech.rule.impl;

import bank.recommendationservice.fintech.repository.RecommendationsRepository;
import bank.recommendationservice.fintech.rule.ProductType;
import bank.recommendationservice.fintech.rule.Rule;
import bank.recommendationservice.fintech.rule.util.RuleUtil;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Правило:
 * Сумма пополнений по всем продуктам типа DEBIT больше,
 * чем сумма трат по всем продуктам типа DEBIT.
 */
@Component
public class DebitDepositsTotalGreaterThanWithdraws implements Rule {
    private final RecommendationsRepository recommendationsRepository;

    public DebitDepositsTotalGreaterThanWithdraws(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    @Override
    public boolean evaluate(UUID userId) {
        Integer debitDepositsTotal = recommendationsRepository.getDepositsOfTypeTotal(userId, ProductType.DEBIT.name());
        RuleUtil.validateNotNull(debitDepositsTotal);
        Integer debitWithdrawsTotal = recommendationsRepository.getWithdrawsOfTypeTotal(userId, ProductType.DEBIT.name());
        RuleUtil.validateNotNull(debitWithdrawsTotal);
        return debitDepositsTotal > debitWithdrawsTotal;
    }
}

package bank.recommendationservice.fintech.rule.impl;

import bank.recommendationservice.fintech.repository.RecommendationsRepository;
import bank.recommendationservice.fintech.rule.ProductType;
import bank.recommendationservice.fintech.rule.Rule;
import bank.recommendationservice.fintech.rule.util.RuleUtil;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Правило:
 * Сумма пополнений по всем продуктам типа DEBIT больше или равна 50 000 ₽
 * ИЛИ
 * Сумма пополнений по всем продуктам типа SAVING больше или равна 50 000 ₽.
 */
@Component
public class DebitOrSavingDepositsTotalGreaterThanOrEqualsTo50_000 implements Rule {
    private final RecommendationsRepository recommendationsRepository;

    public DebitOrSavingDepositsTotalGreaterThanOrEqualsTo50_000(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    @Override
    public boolean evaluate(UUID userId) {
        Integer debitDepositsTotal = recommendationsRepository.getDepositsOfTypeTotal(userId, ProductType.DEBIT.name());
        RuleUtil.validateNotNull(debitDepositsTotal);

        Integer savingDepositsTotal = recommendationsRepository.getDepositsOfTypeTotal(userId, ProductType.SAVING.name());
        RuleUtil.validateNotNull(savingDepositsTotal);
        int threshold = 50000;
        return debitDepositsTotal >= threshold || savingDepositsTotal >= threshold;
    }
}
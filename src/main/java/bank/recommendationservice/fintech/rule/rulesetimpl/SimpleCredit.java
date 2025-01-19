package bank.recommendationservice.fintech.rule.rulesetimpl;

import bank.recommendationservice.fintech.dto.RecommendationDTO;
import bank.recommendationservice.fintech.rule.RecommendationRuleSet;
import bank.recommendationservice.fintech.rule.RuleSetText;
import bank.recommendationservice.fintech.rule.impl.DebitDepositsTotalGreaterThanWithdraws;
import bank.recommendationservice.fintech.rule.impl.DebitWithdrawsTotalGreaterThan100_000;
import bank.recommendationservice.fintech.rule.impl.UsesNoCreditProducts;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static bank.recommendationservice.fintech.rule.RuleSetText.SIMPLE_CREDIT_TEXT;
@Service
public class SimpleCredit implements RecommendationRuleSet {
    private final UsesNoCreditProducts usesNoCreditProducts;
    private final DebitDepositsTotalGreaterThanWithdraws debitDepositsTotalGreaterThanWithdraws;
    private final DebitWithdrawsTotalGreaterThan100_000 debitWithdrawsTotalGreaterThan100_000;

    public SimpleCredit(UsesNoCreditProducts usesNoCreditProducts,
                        DebitDepositsTotalGreaterThanWithdraws debitDepositsTotalGreaterThanWithdraws,
                        DebitWithdrawsTotalGreaterThan100_000 debitWithdrawsTotalGreaterThan100_000) {
        this.usesNoCreditProducts = usesNoCreditProducts;
        this.debitDepositsTotalGreaterThanWithdraws = debitDepositsTotalGreaterThanWithdraws;
        this.debitWithdrawsTotalGreaterThan100_000 = debitWithdrawsTotalGreaterThan100_000;
    }

    @Override
    public RecommendationDTO recommend(UUID userId) {
        if (usesNoCreditProducts.evaluate(userId) &&
                debitDepositsTotalGreaterThanWithdraws.evaluate(userId) &&
                debitWithdrawsTotalGreaterThan100_000.evaluate(userId)) {
            return new RecommendationDTO(UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"),
                    "Простой кредит", SIMPLE_CREDIT_TEXT);
        }
        return new RecommendationDTO();
    }
}

package bank.recommendationservice.fintech.rule.rulesetimpl;

import bank.recommendationservice.fintech.dto.RecommendationDTO;
import bank.recommendationservice.fintech.rule.RecommendationRuleSet;
import bank.recommendationservice.fintech.rule.impl.DebitDepositsTotalGreaterThanWithdraws;
import bank.recommendationservice.fintech.rule.impl.DebitOrSavingDepositsTotalGreaterThanOrEqualsTo50_000;
import bank.recommendationservice.fintech.rule.impl.UsesAtLeastOneDebitProduct;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static bank.recommendationservice.fintech.rule.RuleSetText.TOP_SAVING_TEXT;
@Service
public class TopSaving implements RecommendationRuleSet {
    private final UsesAtLeastOneDebitProduct usesAtLeastOneDebitProduct;
    private final DebitOrSavingDepositsTotalGreaterThanOrEqualsTo50_000 debitOrSavingDepositsTotalGreaterThanOrEqualsTo50_000;
    private final DebitDepositsTotalGreaterThanWithdraws debitDepositsTotalGreaterThanWithdraws;


    public TopSaving(UsesAtLeastOneDebitProduct usesAtLeastOneDebitProduct,
                     DebitOrSavingDepositsTotalGreaterThanOrEqualsTo50_000 debitOrSavingDepositsTotalGreaterThanOrEqualsTo50_000,
                     DebitDepositsTotalGreaterThanWithdraws debitDepositsTotalGreaterThanWithdraws) {
        this.usesAtLeastOneDebitProduct = usesAtLeastOneDebitProduct;
        this.debitOrSavingDepositsTotalGreaterThanOrEqualsTo50_000 = debitOrSavingDepositsTotalGreaterThanOrEqualsTo50_000;
        this.debitDepositsTotalGreaterThanWithdraws = debitDepositsTotalGreaterThanWithdraws;
    }

    @Override
    public RecommendationDTO recommend(UUID userId) {
        if (usesAtLeastOneDebitProduct.evaluate(userId) &&
                debitOrSavingDepositsTotalGreaterThanOrEqualsTo50_000.evaluate(userId) &&
                debitDepositsTotalGreaterThanWithdraws.evaluate(userId)) {
            return new RecommendationDTO(UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925"), "Top Saving", TOP_SAVING_TEXT);
        }
        return new RecommendationDTO();
    }
}

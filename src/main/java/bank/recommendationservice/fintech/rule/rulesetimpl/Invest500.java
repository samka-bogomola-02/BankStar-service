package bank.recommendationservice.fintech.rule.rulesetimpl;

import bank.recommendationservice.fintech.dto.RecommendationDTO;
import bank.recommendationservice.fintech.rule.RecommendationRuleSet;
import bank.recommendationservice.fintech.rule.impl.SavingDepositsTotalGreaterThan1_000;
import bank.recommendationservice.fintech.rule.impl.UsesAtLeastOneDebitProduct;
import bank.recommendationservice.fintech.rule.impl.UsesNoInvestProducts;
import bank.recommendationservice.fintech.rule.RuleSetText;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static bank.recommendationservice.fintech.rule.RuleSetText.INVEST_500_TEXT;
@Service
public class Invest500 implements RecommendationRuleSet {
    private final UsesAtLeastOneDebitProduct usesAtLeastOneDebitProduct;
    private final UsesNoInvestProducts usesNoInvestProducts;
    private final SavingDepositsTotalGreaterThan1_000 savingDepositsTotalGreaterThan1_000;

    public Invest500(UsesAtLeastOneDebitProduct usesAtLeastOneDebitProduct, UsesNoInvestProducts usesNoInvestProducts, SavingDepositsTotalGreaterThan1_000 savingDepositsTotalGreaterThan1000) {
        this.usesAtLeastOneDebitProduct = usesAtLeastOneDebitProduct;
        this.usesNoInvestProducts = usesNoInvestProducts;
        savingDepositsTotalGreaterThan1_000 = savingDepositsTotalGreaterThan1000;
    }


    @Override
    public RecommendationDTO recommend(UUID userId) {
        if (usesAtLeastOneDebitProduct.evaluate(userId) &&
                usesNoInvestProducts.evaluate(userId) &&
                savingDepositsTotalGreaterThan1_000.evaluate(userId)) {
            return new RecommendationDTO(UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"),"Invest500",INVEST_500_TEXT);
        }
        return new RecommendationDTO();
    }
}

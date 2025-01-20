package bank.recommendationservice.fintech.rulesetimpl;

import bank.recommendationservice.fintech.dto.RecommendationDTO;
import bank.recommendationservice.fintech.interfaces.RecommendationRuleSet;
import bank.recommendationservice.fintech.ruleimpl.DebitDepositsTotalGreaterThanWithdraws;
import bank.recommendationservice.fintech.ruleimpl.DebitOrSavingDepositsTotalGreaterThanOrEqualsTo50_000;
import bank.recommendationservice.fintech.ruleimpl.UsesAtLeastOneDebitProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TopSaving implements RecommendationRuleSet {
    private static final Logger logger = LoggerFactory.getLogger(TopSaving.class);

    private final UsesAtLeastOneDebitProduct usesAtLeastOneDebitProduct;
    private final DebitOrSavingDepositsTotalGreaterThanOrEqualsTo50_000 debitOrSavingDepositsTotalGreaterThanOrEqualsTo50_000;
    private final DebitDepositsTotalGreaterThanWithdraws debitDepositsTotalGreaterThanWithdraws;

    public TopSaving(UsesAtLeastOneDebitProduct usesAtLeastOneDebitProduct,
                     DebitOrSavingDepositsTotalGreaterThanOrEqualsTo50_000 debitOrSavingDepositsTotalGreaterThanOrEqualsTo50_000,
                     DebitDepositsTotalGreaterThanWithdraws debitDepositsTotalGreaterThanWithdraws) {
        if (usesAtLeastOneDebitProduct == null) {
            logger.error("Используемый продукт 'usesAtLeastOneDebitProduct' не должен быть null");
            throw new IllegalArgumentException("usesAtLeastOneDebitProduct не должен быть null");
        }
        if (debitOrSavingDepositsTotalGreaterThanOrEqualsTo50_000 == null) {
            logger.error("Используемый продукт 'debitOrSavingDepositsTotalGreaterThanOrEqualsTo50_000' не должен быть null");
            throw new IllegalArgumentException("debitOrSavingDepositsTotalGreaterThanOrEqualsTo50_000 не должен быть null");
        }
        if (debitDepositsTotalGreaterThanWithdraws == null) {
            logger.error("Используемый продукт 'debitDepositsTotalGreaterThanWithdraws' не должен быть null");
            throw new IllegalArgumentException("debitDepositsTotalGreaterThanWithdraws не должен быть null");
        }

        this.usesAtLeastOneDebitProduct = usesAtLeastOneDebitProduct;
        this.debitOrSavingDepositsTotalGreaterThanOrEqualsTo50_000 = debitOrSavingDepositsTotalGreaterThanOrEqualsTo50_000;
        this.debitDepositsTotalGreaterThanWithdraws = debitDepositsTotalGreaterThanWithdraws;
    }

    @Override
    public RecommendationDTO recommend(UUID userId) {
        if (userId == null) {
            logger.error("userId не должен быть null");
            throw new IllegalArgumentException("userId не должен быть null");
        }

        boolean hasDebitProduct = usesAtLeastOneDebitProduct.evaluate(userId);
        boolean depositsGreaterThanOrEqualsTo50k = debitOrSavingDepositsTotalGreaterThanOrEqualsTo50_000.evaluate(userId);
        boolean depositsGreaterThanWithdrawals = debitDepositsTotalGreaterThanWithdraws.evaluate(userId);

        logger.info("Проверка рекомендаций для пользователя с ID: {}", userId);
        logger.info("Использует хотя бы один дебетовый продукт: {}", hasDebitProduct);
        logger.info("Общая сумма дебетовых или сберегательных депозитов больше или равна 50,000: {}", depositsGreaterThanOrEqualsTo50k);
        logger.info("Общая сумма дебетовых депозитов больше суммы снятий: {}", depositsGreaterThanWithdrawals);

        if (hasDebitProduct && depositsGreaterThanOrEqualsTo50k && depositsGreaterThanWithdrawals) {
            logger.info("Рекомендация для пользователя с ID {}: Top Saving", userId);
            return new RecommendationDTO(UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925"),
                    "Top Saving", "Рекомендуем Top Saving.");
        }

        logger.info("Рекомендация для пользователя с ID {}: нет подходящих условий", userId);
        return new RecommendationDTO(); // Возвращаем пустую рекомендацию, если условия не выполнены
    }
}

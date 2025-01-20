package bank.recommendationservice.fintech.rulesetimpl;

import bank.recommendationservice.fintech.dto.RecommendationDTO;
import bank.recommendationservice.fintech.interfaces.RecommendationRuleSet;
import bank.recommendationservice.fintech.ruleimpl.DebitDepositsTotalGreaterThanWithdraws;
import bank.recommendationservice.fintech.ruleimpl.DebitWithdrawsTotalGreaterThan100_000;
import bank.recommendationservice.fintech.ruleimpl.UsesNoCreditProducts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SimpleCredit implements RecommendationRuleSet {
    private static final Logger logger = LoggerFactory.getLogger(SimpleCredit.class);

    private final UsesNoCreditProducts usesNoCreditProducts;
    private final DebitDepositsTotalGreaterThanWithdraws debitDepositsTotalGreaterThanWithdraws;
    private final DebitWithdrawsTotalGreaterThan100_000 debitWithdrawsTotalGreaterThan100_000;

    public SimpleCredit(UsesNoCreditProducts usesNoCreditProducts,
                        DebitDepositsTotalGreaterThanWithdraws debitDepositsTotalGreaterThanWithdraws,
                        DebitWithdrawsTotalGreaterThan100_000 debitWithdrawsTotalGreaterThan100_000) {
        if (usesNoCreditProducts == null) {
            logger.error("Используемый продукт 'usesNoCreditProducts' не должен быть null");
            throw new IllegalArgumentException("usesNoCreditProducts не должен быть null");
        }
        if (debitDepositsTotalGreaterThanWithdraws == null) {
            logger.error("Используемый продукт 'debitDepositsTotalGreaterThanWithdraws' не должен быть null");
            throw new IllegalArgumentException("debitDepositsTotalGreaterThanWithdraws не должен быть null");
        }
        if (debitWithdrawsTotalGreaterThan100_000 == null) {
            logger.error("Используемый продукт 'debitWithdrawsTotalGreaterThan100_000' не должен быть null");
            throw new IllegalArgumentException("debitWithdrawsTotalGreaterThan100_000 не должен быть null");
        }

        this.usesNoCreditProducts = usesNoCreditProducts;
        this.debitDepositsTotalGreaterThanWithdraws = debitDepositsTotalGreaterThanWithdraws;
        this.debitWithdrawsTotalGreaterThan100_000 = debitWithdrawsTotalGreaterThan100_000;
    }

    @Override
    public RecommendationDTO recommend(UUID userId) {
        if (userId == null) {
            logger.error("userId не должен быть null");
            throw new IllegalArgumentException("userId не должен быть null");
        }

        boolean noCreditProducts = usesNoCreditProducts.evaluate(userId);
        boolean depositsGreaterThanWithdraws = debitDepositsTotalGreaterThanWithdraws.evaluate(userId);
        boolean withdrawalsGreaterThan100k = debitWithdrawsTotalGreaterThan100_000.evaluate(userId);

        logger.info("Проверка рекомендаций для пользователя с ID: {}", userId);
        logger.info("Не использует кредитные продукты: {}", noCreditProducts);
        logger.info("Общая сумма дебетовых депозитов больше суммы снятий: {}", depositsGreaterThanWithdraws);
        logger.info("Общая сумма снятий больше 100,000: {}", withdrawalsGreaterThan100k);

        if (noCreditProducts && depositsGreaterThanWithdraws && withdrawalsGreaterThan100k) {
            logger.info("Рекомендация для пользователя с ID {}: Простой кредит", userId);
            return new RecommendationDTO(UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"),
                    "Простой кредит", "Рекомендуем простой кредит.");
        }

        logger.info("Рекомендация для пользователя с ID {}: нет подходящих условий", userId);
        return new RecommendationDTO(); // Возвращаем пустую рекомендацию, если условия не выполнены
    }
}

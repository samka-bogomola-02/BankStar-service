package bank.recommendationservice.fintech.ruleimpl;

import bank.recommendationservice.fintech.ProductType;
import bank.recommendationservice.fintech.repository.RecommendationsRepository;
import bank.recommendationservice.fintech.interfaces.Rule;
import bank.recommendationservice.fintech.util.RuleUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Правило: сумма трат по всем продуктам типа DEBIT должно быть больше 100 000
 */
@Component
public class DebitWithdrawsTotalGreaterThan100_000 implements Rule {
    private static final Logger logger = LoggerFactory.getLogger(DebitWithdrawsTotalGreaterThan100_000.class);

    private final RecommendationsRepository recommendationsRepository;

    public DebitWithdrawsTotalGreaterThan100_000(RecommendationsRepository recommendationsRepository) {
        if (recommendationsRepository == null) {
            logger.error("RecommendationsRepository не должен быть null");
            throw new IllegalArgumentException("recommendationsRepository не должен быть null");
        }
        this.recommendationsRepository = recommendationsRepository;
    }

    @Override
    public boolean evaluate(UUID userId) {
        if (userId == null) {
            logger.error("userId не должен быть null");
            throw new IllegalArgumentException("userId не должен быть null");
        }

        Integer debitWithdrawsTotal = recommendationsRepository.getWithdrawsOfTypeTotal(userId, ProductType.DEBIT.name());
        RuleUtil.validateNotNull(debitWithdrawsTotal);

        int threshold = 100_000;
        boolean result = debitWithdrawsTotal > threshold;

        logger.info("Проверка: Общая сумма дебетовых снятий для пользователя с ID {}: {}", userId, debitWithdrawsTotal);
        logger.info("Результат проверки для пользователя с ID {}: {}", userId, result);

        return result;
    }
}

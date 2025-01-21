package bank.recommendationservice.fintech.ruleimpl;

import bank.recommendationservice.fintech.other.ProductType;
import bank.recommendationservice.fintech.repository.RecommendationsRepository;
import bank.recommendationservice.fintech.interfaces.Rule;
import bank.recommendationservice.fintech.util.RuleUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Правило:
 * Сумма пополнений продуктов с типом SAVING больше 1000 ₽
 */
@Component
public class SavingDepositsTotalGreaterThan1_000 implements Rule {
    private static final Logger logger = LoggerFactory.getLogger(SavingDepositsTotalGreaterThan1_000.class);
    private final RecommendationsRepository recommendationsRepository;

    public SavingDepositsTotalGreaterThan1_000(RecommendationsRepository recommendationsRepository) {
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

        Integer total = recommendationsRepository.getDepositsOfTypeTotal(userId, ProductType.SAVING.name());
        RuleUtil.validateNotNull(total);

        int threshold = 1000;
        boolean result = total > threshold;

        logger.info("Проверка: Общая сумма сбережений для пользователя с ID {}: {}", userId, total);
        logger.info("Результат проверки для пользователя с ID {}: {}", userId, result);

        return result;
    }
}

//package bank.recommendationservice.fintech.ruleimpl;
//
//import bank.recommendationservice.fintech.ProductType;
//import bank.recommendationservice.fintech.repository.RecommendationsRepository;
//import bank.recommendationservice.fintech.interfaces.Rule;
//import bank.recommendationservice.fintech.util.RuleUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import java.util.UUID;
//
///**
// * Правило:
// * Сумма пополнений по всем продуктам типа DEBIT больше,
// * чем сумма трат по всем продуктам типа DEBIT.
// */
//@Component
//public class DebitDepositsTotalGreaterThanWithdraws implements Rule {
//    private static final Logger logger = LoggerFactory.getLogger(DebitDepositsTotalGreaterThanWithdraws.class);
//
//    private final RecommendationsRepository recommendationsRepository;
//
//    public DebitDepositsTotalGreaterThanWithdraws(RecommendationsRepository recommendationsRepository) {
//        if (recommendationsRepository == null) {
//            logger.error("RecommendationsRepository не должен быть null");
//            throw new IllegalArgumentException("recommendationsRepository не должен быть null");
//        }
//        this.recommendationsRepository = recommendationsRepository;
//    }
//
//    @Override
//    public boolean evaluate(UUID userId) {
//        if (userId == null) {
//            logger.error("userId не должен быть null");
//            throw new IllegalArgumentException("userId не должен быть null");
//        }
//
//        Integer debitDepositsTotal = recommendationsRepository.getDepositsOfTypeTotal(userId, ProductType.DEBIT.name());
//        RuleUtil.validateNotNull(debitDepositsTotal);
//
//        Integer debitWithdrawsTotal = recommendationsRepository.getWithdrawsOfTypeTotal(userId, ProductType.DEBIT.name());
//        RuleUtil.validateNotNull(debitWithdrawsTotal);
//
//        logger.info("Проверка: Общая сумма дебетовых депозитов: {}, Общая сумма дебетовых снятий: {}", debitDepositsTotal, debitWithdrawsTotal);
//
//        boolean result = debitDepositsTotal > debitWithdrawsTotal;
//        logger.info("Результат проверки для пользователя с ID {}: {}", userId, result);
//
//        return result;
//    }
//}


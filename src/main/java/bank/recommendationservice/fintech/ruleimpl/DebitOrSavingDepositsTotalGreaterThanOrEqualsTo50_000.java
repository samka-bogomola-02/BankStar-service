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
//@Component
//public class DebitOrSavingDepositsTotalGreaterThanOrEqualsTo50_000 implements Rule {
//    private static final Logger logger = LoggerFactory.getLogger(DebitOrSavingDepositsTotalGreaterThanOrEqualsTo50_000.class);
//
//    private final RecommendationsRepository recommendationsRepository;
//
//    public DebitOrSavingDepositsTotalGreaterThanOrEqualsTo50_000(RecommendationsRepository recommendationsRepository) {
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
//        Integer savingDepositsTotal = recommendationsRepository.getDepositsOfTypeTotal(userId, ProductType.SAVING.name());
//        RuleUtil.validateNotNull(savingDepositsTotal);
//
//        logger.info("Проверка: Общая сумма дебетовых депозитов: {}, Общая сумма сберегательных депозитов: {}", debitDepositsTotal, savingDepositsTotal);
//
//        int threshold = 50000;
//        boolean result = debitDepositsTotal >= threshold || savingDepositsTotal >= threshold;
//
//        logger.info("Результат проверки для пользователя с ID {}: {}", userId, result);
//
//        return result;
//    }
//}

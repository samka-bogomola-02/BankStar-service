//package bank.recommendationservice.fintech.rulesetimpl;
//
//import bank.recommendationservice.fintech.dto.RecommendationDTO;
//import bank.recommendationservice.fintech.interfaces.RecommendationRuleSet;
//import bank.recommendationservice.fintech.ruleimpl.SavingDepositsTotalGreaterThan1_000;
//import bank.recommendationservice.fintech.ruleimpl.UsesAtLeastOneDebitProduct;
//import bank.recommendationservice.fintech.ruleimpl.UsesNoInvestProducts;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import java.util.UUID;
//
//@Service
//public class Invest500 implements RecommendationRuleSet {
//    private static final Logger logger = LoggerFactory.getLogger(Invest500.class);
//
//    private final UsesAtLeastOneDebitProduct usesAtLeastOneDebitProduct;
//    private final UsesNoInvestProducts usesNoInvestProducts;
//    private  final SavingDepositsTotalGreaterThan1_000 savingDepositsTotalGreaterThan1_000;
//
//public Invest500(UsesAtLeastOneDebitProduct usesAtLeastOneDebitProduct,
//                 UsesNoInvestProducts usesNoInvestProducts,
//                 SavingDepositsTotalGreaterThan1_000 savingDepositsTotalGreaterThan1000) {
//    if (usesAtLeastOneDebitProduct == null) {
//        logger.error("Используемый продукт 'usesAtLeastOneDebitProduct' не должен быть null");
//        throw new IllegalArgumentException("usesAtLeastOneDebitProduct не должен быть null");
//    }
//    if (usesNoInvestProducts == null) {
//        logger.error("Используемый продукт 'usesNoInvestProducts' не должен быть null");
//        throw new IllegalArgumentException("usesNoInvestProducts не должен быть null");
//    }
//    if (savingDepositsTotalGreaterThan1000 == null) {
//        logger.error("Используемый продукт 'savingDepositsTotalGreaterThan1_000' не должен быть null");
//        throw new IllegalArgumentException("savingDepositsTotalGreaterThan1_000 не должен быть null");
//    }
//
//    this.usesAtLeastOneDebitProduct = usesAtLeastOneDebitProduct;
//    this.usesNoInvestProducts = usesNoInvestProducts;
//    this.savingDepositsTotalGreaterThan1_000 = savingDepositsTotalGreaterThan1000;
//}
//
//    @Override
//    public RecommendationDTO recommend(UUID userId) {
//        if (userId == null) {
//            logger.error("userId не должен быть null");
//            throw new IllegalArgumentException("userId не должен быть null");
//        }
//
//        boolean hasDebitProduct = usesAtLeastOneDebitProduct.evaluate(userId);
//        boolean hasNoInvestProducts = usesNoInvestProducts.evaluate(userId);
//        boolean hasSufficientSavings = savingDepositsTotalGreaterThan1_000.evaluate(userId);
//
//        logger.info("Проверка рекомендаций для пользователя с ID: {}", userId);
//        logger.info("Использует хотя бы один дебетовый продукт: {}", hasDebitProduct);
//        logger.info("Не использует инвестиционные продукты: {}", hasNoInvestProducts);
//        logger.info("Общая сумма сбережений больше 1000: {}", hasSufficientSavings);
//
//        if (hasDebitProduct && hasNoInvestProducts && hasSufficientSavings) {
//            logger.info("Рекомендация для пользователя с ID {}: Invest500", userId);
//            return new RecommendationDTO(UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"),
//                    "Invest500", "Рекомендуем инвестировать 500.");
//        }
//
//        logger.info("Рекомендация для пользователя с ID {}: нет подходящих условий", userId);
//        return new RecommendationDTO(); // Возвращаем пустую рекомендацию, если условия не выполнены
//    }
//}

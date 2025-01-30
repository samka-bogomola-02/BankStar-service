package bank.recommendationservice.fintech.service;


import bank.recommendationservice.fintech.dto.RecommendationDTO;
import bank.recommendationservice.fintech.exception.NullArgumentException;
import bank.recommendationservice.fintech.exception.UnknownQueryTypeException;
import bank.recommendationservice.fintech.interfaces.RecommendationRuleSet;
import bank.recommendationservice.fintech.model.DynamicRule;
import bank.recommendationservice.fintech.model.DynamicRuleQuery;
import bank.recommendationservice.fintech.other.ComparisonType;
import bank.recommendationservice.fintech.other.ProductType;
import bank.recommendationservice.fintech.other.QueryType;
import bank.recommendationservice.fintech.other.TransactionType;
import bank.recommendationservice.fintech.repository.DynamicRuleRepository;
import bank.recommendationservice.fintech.repository.RecommendationsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RecommendationService {
    @Autowired
    private List<RecommendationRuleSet> ruleSets;

    @Autowired
    DynamicRuleRepository dynamicRuleRepository;

    @Autowired
    RecommendationsRepository recommendationsRepository;
    private static final Logger logger = LoggerFactory.getLogger(RecommendationService.class);

    /**
     * Получение списка рекомендаций для пользователя
     *
     * @param userId - уникальный идентификатор пользователя
     * @return список рекомендаций для пользователя
     */

    public List<RecommendationDTO> getRecommendations(UUID userId) {
        logger.info("Вызван метод getRecommendations для пользователя с ID: {}", userId);
        List<DynamicRule> dynamicRules = dynamicRuleRepository.findAll();
        List<RecommendationDTO> result = ruleSets.stream()
                .map(p -> p.recommend(userId))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        logger.debug("Количество найденных рекомендаций для пользователя с ID: {}: {}", userId, result.size());
        return result;
    }


    /**
     * Оценивает динамические правила для заданного пользователя.
     *
     * @param rule  - динамическое правило для оценки
     * @param userId - идентификатор пользователя
     * @return true, если правило оценивается как true, false в противном случае
     * @throws NullArgumentException если rule - null
     */
    public boolean evaluateDynamicRules(DynamicRule rule, UUID userId) {
        if (rule == null) {
            logger.warn("Динамическое правило null");
            throw new NullArgumentException("Динамическое правило не может быть null");
        }
        List<DynamicRuleQuery> queries = rule.getQueries();
        if (queries.isEmpty()) {
            logger.debug("Список запросов для динамического правила {} пустой", rule);
            return false;
        }
        for (DynamicRuleQuery query : queries) {
            try {
                QueryType queryType = QueryType.fromString(query.getQuery());
                List<String> queryArguments = query.getArguments();
                return switch (queryType) {
                    case USER_OF -> processUserOfQuery(userId, queryArguments.get(0));
                    case ACTIVE_USER_OF -> processActiveUserOfQuery(ProductType.valueOf(queryArguments.get(0)), userId);
                    case TRANSACTION_SUM_COMPARE -> processTransactionSumCompare(ProductType.valueOf(queryArguments.get(0)),
                            TransactionType.valueOf(queryArguments.get(1)), userId, ComparisonType.valueOf(queryArguments.get(2)), Integer.parseInt(queryArguments.get(3)));

                    case TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW -> processTransactionSumCompareDepositWithdraw(ProductType.valueOf(queryArguments.get(0)),
                            userId, ComparisonType.valueOf(queryArguments.get(1)));

                };

            } catch (UnknownQueryTypeException e) {
                logger.warn("Неизвестный тип запроса query: {}", query.getQuery());
                return false;
            }
        }
        return false;
    }

    private boolean processUserOfQuery(UUID userId, String productType) {
        return recommendationsRepository.usesProductOfType(userId, productType);
    }

    private boolean processActiveUserOfQuery(ProductType productType, UUID userId) {
        return recommendationsRepository.isActiveUserOfProduct(productType, userId);
    }

    private boolean processTransactionSumCompare(ProductType productType, TransactionType transactionType,
                                                 UUID userId, ComparisonType comparisonType, int constant) {
        return recommendationsRepository.compareTransactionSum(productType, transactionType, userId, comparisonType, constant);
    }

    private boolean processTransactionSumCompareDepositWithdraw(ProductType productType, UUID userId, ComparisonType comparisonType) {
        return recommendationsRepository.compareDepositWithdrawSum(productType, userId, comparisonType);
    }
    }
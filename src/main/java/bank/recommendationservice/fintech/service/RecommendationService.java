package bank.recommendationservice.fintech.service;


import bank.recommendationservice.fintech.dto.RecommendationDTO;
import bank.recommendationservice.fintech.rulesetimpl.Invest500;
import bank.recommendationservice.fintech.rulesetimpl.SimpleCredit;
import bank.recommendationservice.fintech.rulesetimpl.TopSaving;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RecommendationService {
    private final Invest500 invest500;
    private final SimpleCredit simpleCredit;
    private final TopSaving topSaving;
    private static final Logger logger = LoggerFactory.getLogger(RecommendationService.class);

    public RecommendationService(Invest500 invest500, SimpleCredit simpleCredit, TopSaving topSaving) {
        this.invest500 = invest500;
        this.simpleCredit = simpleCredit;
        this.topSaving = topSaving;
    }
    public List<RecommendationDTO> getRecommendations(UUID userId) {
        logger.info("Запрос рекомендаций для пользователя с ID: {}", userId);
        List<RecommendationDTO> result = new ArrayList<>();

        RecommendationDTO investRecommendation = invest500.recommend(userId);
        if (investRecommendation != null) {
            result.add(investRecommendation);
            logger.info("Добавлена инвестиционная рекомендация: {}", investRecommendation);
        } else {
            logger.warn("Инвестиционная рекомендация не найдена для пользователя с ID: {}", userId);
        }

        RecommendationDTO creditRecommendation = simpleCredit.recommend(userId);
        if (creditRecommendation != null) {
            result.add(creditRecommendation);
            logger.info("Добавлена кредитная рекомендация: {}", creditRecommendation);
        } else {
            logger.warn("Кредитная рекомендация не найдена для пользователя с ID: {}", userId);
        }

        RecommendationDTO savingRecommendation = topSaving.recommend(userId);
        if (savingRecommendation != null) {
            result.add(savingRecommendation);
            logger.info("Добавлена сберегательная рекомендация: {}", savingRecommendation);
        } else {
            logger.warn("Сберегательная рекомендация не найдена для пользователя с ID: {}", userId);
        }

        logger.info("Количество найденных рекомендаций: {}", result.size());
        return result;
    }
}

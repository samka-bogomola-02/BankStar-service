package bank.recommendationservice.fintech.service;


import bank.recommendationservice.fintech.dto.RecommendationDTO;
import bank.recommendationservice.fintech.interfaces.RecommendationRuleSet;
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


    private static final Logger logger = LoggerFactory.getLogger(RecommendationService.class);

    public List<RecommendationDTO> getRecommendations(UUID userId) {
        logger.info("Запрос рекомендаций для пользователя с ID: {}", userId);

        List<RecommendationDTO> result = ruleSets.stream()
                .map(provider -> {
                    RecommendationDTO recommendation = provider.recommend(userId);
                    if (recommendation != null) {
                        logger.info("Добавлена рекомендация от {}: {}", provider.getClass().getSimpleName(), recommendation);
                    } else {
                        logger.warn("Рекомендация не найдена для пользователя с ID: {} от {}", userId, provider.getClass().getSimpleName());
                    }
                    return recommendation;
                })
                .filter(Objects::nonNull) // Убираем null значения
                .collect(Collectors.toList()); // Собираем результат в список

        logger.info("Количество найденных рекомендаций: {}", result.size());
        return result;
    }
}

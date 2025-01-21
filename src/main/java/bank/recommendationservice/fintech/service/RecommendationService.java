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
        logger.info("Вызван метод getRecommendations для пользователя с ID: {}", userId);
        List<RecommendationDTO> result = ruleSets.stream()
                .map(p-> p.recommend(userId))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        logger.debug("Количество найденных рекомендаций для пользователя с ID: {}: {}", userId, result.size());
        return result;
    }
}

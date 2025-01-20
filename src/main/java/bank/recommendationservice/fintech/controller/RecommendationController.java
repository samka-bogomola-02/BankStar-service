package bank.recommendationservice.fintech.controller;

import bank.recommendationservice.fintech.dto.RecommendationDTO;
import bank.recommendationservice.fintech.model.RecommendationResponse;
import bank.recommendationservice.fintech.service.RecommendationServiceNew;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

    private final RecommendationServiceNew recommendationService;

    public RecommendationController(RecommendationServiceNew recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<RecommendationResponse> getRecommendations(@PathVariable("user_id") UUID userId) {
        List<RecommendationDTO> recommendations = recommendationService.getRecommendations(userId);
        RecommendationResponse response = new RecommendationResponse(userId, recommendations);
        return ResponseEntity.ok(response);
    }
}

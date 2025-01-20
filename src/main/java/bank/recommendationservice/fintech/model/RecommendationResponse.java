package bank.recommendationservice.fintech.model;

import bank.recommendationservice.fintech.dto.RecommendationDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
@Getter
@Setter
public class RecommendationResponse {
    private UUID user_id;
    private List<RecommendationDTO> recommendations;

    public RecommendationResponse(UUID user_id, List<RecommendationDTO> recommendations) {
        this.user_id = user_id;
        this.recommendations = recommendations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecommendationResponse that = (RecommendationResponse) o;
        return Objects.equals(user_id, that.user_id) && Objects.equals(recommendations, that.recommendations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, recommendations);
    }

    @Override
    public String toString() {
        return "RecommendationResponse{" +
                "user_id=" + user_id +
                ", recommendations=" + recommendations +
                '}';
    }
}


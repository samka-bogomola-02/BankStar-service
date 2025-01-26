package bank.recommendationservice.fintech.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "recommendation_rule")
@Getter
@Setter
public class RecommendationRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "product_text", columnDefinition = "TEXT")
    private String productText;

    @OneToMany(mappedBy = "recommendationRule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DynamicRule> rule;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecommendationRule that = (RecommendationRule) o;
        return Objects.equals(id, that.id)
                && Objects.equals(productName, that.productName)
                && Objects.equals(productId, that.productId)
                && Objects.equals(productText, that.productText)
                && Objects.equals(rule, that.rule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, productId, productText, rule);
    }

    @Override
    public String toString() {
        return "RecommendationRule{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productId='" + productId + '\'' +
                ", productText='" + productText + '\'' +
                ", rule=" + rule +
                '}';
    }
}

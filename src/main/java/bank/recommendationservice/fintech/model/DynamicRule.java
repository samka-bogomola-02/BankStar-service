package bank.recommendationservice.fintech.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "dynamic_rule")
@Getter
@Setter
public class DynamicRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recommendation_rule_id", nullable = false)
    @JsonIgnore
    private RecommendationRule recommendationRule;

    @Column(name = "query", nullable = false)
    private String query;

    @ElementCollection
    @CollectionTable(name = "dynamic_rule_arguments", joinColumns = @JoinColumn(name = "dynamic_rule_id"))
    @Column(name = "arguments", nullable = false)
    private List<String> arguments;

    @Column(name = "negate", nullable = false)
    private boolean negate;

    public DynamicRule() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DynamicRule that = (DynamicRule) o;
        return negate == that.negate && Objects.equals(id, that.id) && Objects.equals(recommendationRule, that.recommendationRule) && Objects.equals(query, that.query) && Objects.equals(arguments, that.arguments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, recommendationRule, query, arguments, negate);
    }

    @Override
    public String toString() {
        return "DynamicRule{" +
                "id=" + id +
                ", recommendationRule=" + recommendationRule +
                ", query='" + query + '\'' +
                ", arguments=" + arguments +
                ", negate=" + negate +
                '}';
    }
}

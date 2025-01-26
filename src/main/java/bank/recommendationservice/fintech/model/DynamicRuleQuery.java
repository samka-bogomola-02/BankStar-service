package bank.recommendationservice.fintech.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "dynamic_rule_query")
@Getter
@Setter
public class DynamicRuleQuery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name = "query", nullable = false)
    private String query;

    @ElementCollection
    @CollectionTable(name = "dynamic_rule_query_arguments", joinColumns = @JoinColumn(name = "dynamic_rule_query_id"))
    @Column(name = "argument", nullable = false)
    private List<String> arguments;

    @Column(name = "negate", nullable = false)
    private boolean negate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "dynamic_rule_id")
    private DynamicRule dynamicRule;

    public DynamicRuleQuery() {
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        DynamicRuleQuery that = (DynamicRuleQuery) object;
        return negate == that.negate && Objects.equals(id, that.id) && Objects.equals(query, that.query) && Objects.equals(arguments, that.arguments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, query, arguments, negate);
    }

    @Override
    public String toString() {
        return "DynamicRuleQuery{" +
                "id=" + id +
                ", query='" + query + '\'' +
                ", arguments=" + arguments +
                ", negate=" + negate +
                '}';
    }
}

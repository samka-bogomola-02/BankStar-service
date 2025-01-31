package bank.recommendationservice.fintech.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "dynamic_rule_query")
@Getter
@Setter
public class DynamicRuleQuery {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dynamic_rule_query_seq_gen")
    @SequenceGenerator(name = "dynamic_rule_query_seq_gen", sequenceName = "dynamic_rule_query_seq", allocationSize = 1)
    @JsonIgnore
    private Long id;

    @Column(name = "query", nullable = false)
    private String query;


    @ElementCollection
    @CollectionTable(name = "query_arguments", joinColumns = @JoinColumn(name = "dynamic_rule_query_id"))
    private List<String> arguments;

    @Column(name = "negate", nullable = false)
    private boolean negate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "dynamic_rule_id", nullable = false)
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

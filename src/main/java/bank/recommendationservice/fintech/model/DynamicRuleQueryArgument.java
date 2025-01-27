package bank.recommendationservice.fintech.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "dynamic_rule_argument")
@Getter
@Setter
public class DynamicRuleQueryArgument {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dynamic_rule_argument_seq_gen")
    @SequenceGenerator(name = "dynamic_rule_argument_seq_gen", sequenceName = "dynamic_rule_argument_seq", allocationSize = 1)
    @JsonIgnore
    private Long id;

    @Column(name = "argument_text", nullable = false)
    private String argumentText;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "dynamic_rule_query_id", nullable = false)
    private DynamicRuleQuery dynamicRuleQuery;

    public DynamicRuleQueryArgument() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DynamicRuleQueryArgument that = (DynamicRuleQueryArgument) o;
        return Objects.equals(id, that.id) && Objects.equals(argumentText, that.argumentText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, argumentText);
    }

    @Override
    public String toString() {
        return "DynamicRuleQueryArgument{" +
                "id=" + id +
                ", argumentText='" + argumentText + '\'' +
                '}';
    }
}

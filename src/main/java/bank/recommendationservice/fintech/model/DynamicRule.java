package bank.recommendationservice.fintech.model;

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
    private Long id;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "dynamic_rule_id")
    private List<Query> queries;

    public DynamicRule() {
    }

    public DynamicRule(List<Query> queries) {
        this.queries = queries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DynamicRule that = (DynamicRule) o;
        return Objects.equals(id, that.id) && Objects.equals(queries, that.queries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, queries);
    }

    @Override
    public String toString() {
        return "DynamicRule{" +
                "id=" + id +
                ", queries=" + queries +
                '}';
    }
}

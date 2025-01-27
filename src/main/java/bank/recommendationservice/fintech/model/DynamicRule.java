package bank.recommendationservice.fintech.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "dynamic_rule")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DynamicRule {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dynamic_rule_seq_gen")
    @SequenceGenerator(name = "dynamic_rule_seq_gen", sequenceName = "dynamic_rule_seq", allocationSize = 1)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "product_text", nullable = false)
    private String productText;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "dynamic_rule_id")
    @JsonProperty("rule")
    private List<DynamicRuleQuery> queries;
    public DynamicRule(String productName, UUID productId, String productText, List<DynamicRuleQuery> queries) {
        this.productName = productName;
        this.productId = productId;
        this.productText = productText;
        this.queries = queries;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        DynamicRule that = (DynamicRule) object;
        return Objects.equals(id, that.id) && Objects.equals(productName, that.productName) && Objects.equals(productId, that.productId) && Objects.equals(productText, that.productText) && Objects.equals(queries, that.queries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, productId, productText, queries);
    }

    @Override
    public String toString() {
        return "DynamicRule{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productId=" + productId +
                ", productText='" + productText + '\'' +
                ", queries=" + queries +
                '}';
    }
    public void addQuery(DynamicRuleQuery query) {
        queries.add(query);
        query.setDynamicRule(this); // Устанавливаем связь с текущим правилом
    }

    public void removeQuery(DynamicRuleQuery query) {
        queries.remove(query);
        query.setDynamicRule(null); // Убираем связь с текущим правилом
    }
}

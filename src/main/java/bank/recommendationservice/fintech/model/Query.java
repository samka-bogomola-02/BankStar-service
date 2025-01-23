package bank.recommendationservice.fintech.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "query")
public class Query {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "query", nullable = false)
    private String query;
    @ElementCollection
    @CollectionTable(name = "query_arguments", joinColumns = @JoinColumn(name = "query_id"))
    @Column(name = "argument")
    private List<String> arguments;
    @Column(name = "negate", nullable = false)
    private boolean negate;
    public Query() {
    }

    public Query(String query, List<String> arguments, boolean negate) {
        this.query = query;
        this.arguments = arguments;
        this.negate = negate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Query query1 = (Query) o;
        return negate == query1.negate && Objects.equals(query, query1.query) && Objects.equals(arguments, query1.arguments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(query, arguments, negate);
    }

    @Override
    public String toString() {
        return "Query{" +
                "query='" + query + '\'' +
                ", arguments=" + arguments +
                ", negate=" + negate +
                '}';
    }
}

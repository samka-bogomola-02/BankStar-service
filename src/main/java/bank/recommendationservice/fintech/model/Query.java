package bank.recommendationservice.fintech.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
public class Query {
    private String query;
    private List<String> arguments;
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

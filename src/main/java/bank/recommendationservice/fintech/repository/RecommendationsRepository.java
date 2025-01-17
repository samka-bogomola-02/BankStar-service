package bank.recommendationservice.fintech.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class RecommendationsRepository {
    private final JdbcTemplate jdbcTemplate;

    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Проверка, есть ли у пользователя хотя бы одна транзакция с продуктом типа DEBIT
     * Реализация с помощью SQL-запроса. JOIN-запрос к таблице transactions и таблице products.
     * @param userId id пользователя
     * @return {@code true} если есть; {@code false}, если нет
     */
    public boolean userHasAtLeastOneDebitProduct(UUID userId) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject("SELECT COUNT (*) FROM transactions t JOIN products p ON t.PRODUCT_ID = p.id WHERE t.USER_ID = ? AND p.TYPE = 'DEBIT'",
                Boolean.class,
                userId));
    }
}

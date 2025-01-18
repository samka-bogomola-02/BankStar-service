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
     * Проверка, есть ли в базе данных хотя бы одна транзакция типа productType у пользователя с id userId
     *
     * @param userId      - ID пользователя
     * @param productType - тип продукта
     * @return {@code true} если есть; {@code false}, если нет
     */
    public boolean usesProductOfType(UUID userId, String productType) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM transactions t JOIN products p ON t.PRODUCT_ID = p.ID WHERE t.USER_ID = ? AND p.TYPE = ?",
                Boolean.class,
                userId,
                productType));
    }

    /**
     * Возвращает сумму всех пополнений (депозитов) по продукту типа productType у пользователя с id UserId
     *
     * @param userId      - id пользователя
     * @param productType - тип продукта
     * @return Integer со значением суммы всех пополнений
     */
    public Integer getDepositsOfTypeTotal(UUID userId, String productType) {
        return jdbcTemplate.queryForObject("SELECT SUM (*) FROM transactions t JOIN products p ON t.PRODUCT_ID = p.id WHERE t.USER_ID = ? AND p.TYPE = ? AND t.TYPE = 'DEPOSIT'",
                Integer.class,
                userId,
                productType);
    }

    /**
     * Возвращает сумму всех трат (withdraw) по продукту типа productType у пользователя с id UserId
     *
     * @param userId      - id пользователя
     * @param productType - тип продукта
     * @return Integer со значением суммы всех трат
     */
    public Integer getWithdrawsOfTypeTotal(UUID userId, String productType) {
        return jdbcTemplate.queryForObject("SELECT SUM (*) FROM transactions t JOIN products p ON t.PRODUCT_ID = p.id WHERE t.USER_ID = ? AND p.TYPE = ? AND t.TYPE = 'WITHDRAW'",
                Integer.class,
                userId,
                productType);
    }

}

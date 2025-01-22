package bank.recommendationservice.fintech.repository;

import bank.recommendationservice.fintech.exception.NullArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class RecommendationsRepository {
    private static final Logger logger = LoggerFactory.getLogger(RecommendationsRepository.class);
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
        logger.info("Вызван метод usesProductOfType() с параметрами userId={}, productType={}", userId, productType);
        if (userId == null) {
            throw new NullArgumentException("userId не должен быть null");
        }
        if (productType == null) {
            throw new NullArgumentException("productType не должен быть null");
        }

        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(t.amount) " +
                        "FROM transactions t JOIN products p ON t.PRODUCT_ID = p.ID WHERE t.USER_ID = ? AND p.TYPE = ?",
                Integer.class,
                userId,
                productType);

        return count != null && count > 0;
    }

    /**
     * Возвращает сумму всех пополнений (депозитов) по продукту типа productType у пользователя с id userId
     *
     * @param userId      - id пользователя
     * @param productType - тип продукта
     * @return int со значением суммы всех пополнений
     */
    public int getDepositsOfTypeTotal(UUID userId, String productType) {
        logger.info("Вызван метод getDepositsOfTypeTotal() с параметрами userId={}, productType={}", userId, productType);
        if (userId == null) {
            throw new NullArgumentException("userId не должен быть null");
        }
        if (productType == null) {
            throw new NullArgumentException("productType не должен быть null");
        }

        Integer total = jdbcTemplate.queryForObject("SELECT SUM(t.amount) " +
                        "FROM transactions t JOIN products p ON t.product_id = p.id " +
                        "WHERE t.user_id = ? AND p.type = ? AND t.type = 'DEPOSIT';",
                Integer.class,
                userId,
                productType);

        return total != null ? total : 0;
    }

    /**
     * Возвращает сумму всех трат (withdraw) по продукту типа productType у пользователя с id userId
     *
     * @param userId      - id пользователя
     * @param productType - тип продукта
     * @return int со значением суммы всех трат
     */
    public int getWithdrawsOfTypeTotal(UUID userId, String productType) {
        logger.info("Вызван метод getWithdrawsOfTypeTotal() с параметрами userId={}, productType={}", userId, productType);
        if (userId == null) {
            throw new NullArgumentException("userId не должен быть null");
        }
        if (productType == null) {
            throw new NullArgumentException("productType не должен быть null");
        }
        Integer total = jdbcTemplate.queryForObject("SELECT SUM(t.amount) " +
                        "FROM transactions t JOIN products p ON t.PRODUCT_ID = p.id " +
                        "WHERE t.USER_ID = ? AND p.TYPE = ? AND t.TYPE = 'WITHDRAW'",
                Integer.class,
                userId,
                productType);

        return total != null ? total : 0;
    }
}


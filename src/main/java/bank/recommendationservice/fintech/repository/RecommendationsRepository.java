package bank.recommendationservice.fintech.repository;

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
        if (jdbcTemplate == null) {
            logger.error("JdbcTemplate не должен быть null");
            throw new IllegalArgumentException("jdbcTemplate не должен быть null");
        }
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
        if (userId == null) {
            logger.error("userId не должен быть null");
            throw new IllegalArgumentException("userId не должен быть null");
        }
        if (productType == null) {
            logger.error("productType не должен быть null");
            throw new IllegalArgumentException("productType не должен быть null");
        }

        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(t.amount) " +
                        "FROM transactions t JOIN products p ON t.PRODUCT_ID = p.ID WHERE t.USER_ID = ? AND p.TYPE = ?",
                Integer.class,
                userId,
                productType);

        boolean result = count != null && count > 0;

        logger.info("Проверка: Пользователь с ID {} использует продукт типа {}: {}", userId, productType, result);

        return result;
    }

    /**
     * Возвращает сумму всех пополнений (депозитов) по продукту типа productType у пользователя с id userId
     *
     * @param userId      - id пользователя
     * @param productType - тип продукта
     * @return Integer со значением суммы всех пополнений
     */
    public Integer getDepositsOfTypeTotal(UUID userId, String productType) {
        if (userId == null) {
            logger.error("userId не должен быть null");
            throw new IllegalArgumentException("userId не должен быть null");
        }
        if (productType == null) {
            logger.error("productType не должен быть null");
            throw new IllegalArgumentException("productType не должен быть null");
        }

        Integer total = jdbcTemplate.queryForObject("SELECT SUM(t.amount) " +
                        "FROM transactions t JOIN products p ON t.product_id = p.id " +
                        "WHERE t.user_id = ? AND p.type = ? AND t.type = 'DEPOSIT';",
                Integer.class,
                userId,
                productType);

        logger.info("Сумма всех депозитов для пользователя с ID {} по продукту типа {}: {}", userId, productType, total);

        return total;
    }

    /**
     * Возвращает сумму всех трат (withdraw) по продукту типа productType у пользователя с id userId
     *
     * @param userId      - id пользователя
     * @param productType - тип продукта
     * @return Integer со значением суммы всех трат
     */
    public Integer getWithdrawsOfTypeTotal(UUID userId, String productType) {
        if (userId == null) {
            logger.error("userId не должен быть null");
            throw new IllegalArgumentException("userId не должен быть null");
        }
        if (productType == null) {
            logger.error("productType не должен быть null");
            throw new IllegalArgumentException("productType не должен быть null");
        }
        Integer total = jdbcTemplate.queryForObject("SELECT SUM(t.amount) " +
                        "FROM transactions t JOIN products p ON t.PRODUCT_ID = p.id " +
                        "WHERE t.USER_ID = ? AND p.TYPE = ? AND t.TYPE = 'WITHDRAW'",
                Integer.class,
                userId,
                productType);

        logger.info("Сумма всех трат для пользователя с ID {} по продукту типа {}: {}", userId, productType, total);

        return total;
    }

    // Метод для получения суммы пополнений по продуктам типа DEBIT
    public Double getDepositSumByType(UUID userId, String productType) {
        String sql = "SELECT SUM(t.amount) FROM transactions t " +
                "JOIN products p ON t.product_id = p.id " +
                "WHERE t.user_id = ? AND p.type = ? AND t.transactions = 'DEPOSIT'";

        logger.info("Запрос суммы пополнений для пользователя {} по продукту типа {}", userId, productType);
        Double sum = jdbcTemplate.queryForObject(sql, new Object[]{userId, productType}, Double.class);
        logger.info("Сумма пополнений для пользователя {} по продукту типа {}: {}", userId, productType, sum);
        return sum;
    }

    // Метод для получения суммы трат по продуктам типа DEBIT
    public Double getWithdrawalSumByType(UUID userId, String productType) {
        String sql = "SELECT SUM(t.amount) FROM transactions t " +
                "JOIN products p ON t.product_id = p.id " +
                "WHERE t.user_id = ? AND p.type = ? AND t.transactions = 'WITHDRAWAL'";

        logger.info("Запрос суммы трат для пользователя {} по продукту типа {}", userId, productType);
        Double sum = jdbcTemplate.queryForObject(sql, new Object[]{userId, productType}, Double.class);
        logger.info("Сумма трат для пользователя {} по продукту типа {}: {}", userId, productType, sum);
        return sum;
    }

    // Метод для получения суммы пополнений по всем продуктам типа SAVING
    public Double getTotalSavingDepositSum(UUID userId) {
        logger.info("Запрос суммы пополнений по всем продуктам типа SAVING для пользователя {}", userId);
        Double sum = getDepositSumByType(userId, "SAVING");
        logger.info("Сумма пополнений по всем продуктам типа SAVING для пользователя {}: {}", userId, sum);
        return sum;
    }

    // Метод для получения суммы пополнений по всем продуктам типа DEBIT
    public Double getTotalDebitDepositSum(UUID userId) {
        logger.info("Запрос суммы пополнений по всем продуктам типа DEBIT для пользователя {}", userId);
        Double sum = getDepositSumByType(userId, "DEBIT");
        logger.info("Сумма пополнений по всем продуктам типа DEBIT для пользователя {}: {}", userId, sum);
        return sum;
    }

    public int countDebitTransactions(UUID userId) {
        // SQL-запрос для подсчета количества транзакций типа DEBIT для указанного пользователя
        String sql = "SELECT COUNT(*) FROM transactions WHERE user_id = ? AND type = 'DEBIT'";
        return jdbcTemplate.queryForObject(sql, new Object[]{userId}, Integer.class);
    }

    public Double sumDepositTransactions(UUID userId, String type) {
        // SQL-запрос для суммирования всех пополнений (DEPOSIT) для указанного типа транзакции и пользователя
        String sql = "SELECT SUM(amount) FROM transactions WHERE user_id = ? AND type = ? AND operation = 'DEPOSIT'";
        return jdbcTemplate.queryForObject(sql, new Object[]{userId, type}, Double.class);
    }

    public Double sumWithdrawalTransactions(UUID userId, String type) {
        // SQL-запрос для суммирования всех трат (WITHDRAWAL) для указанного типа транзакции и пользователя
        String sql = "SELECT SUM(amount) FROM transactions WHERE user_id = ? AND type = ? AND operation = 'WITHDRAWAL'";
        return jdbcTemplate.queryForObject(sql, new Object[]{userId, type}, Double.class);
    }
}


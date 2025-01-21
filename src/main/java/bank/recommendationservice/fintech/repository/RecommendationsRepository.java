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
        logger.info("Входные данные: userId={}, productType={}", userId, productType);
        if (userId == null) {
            throw new IllegalArgumentException("userId не должен быть null");
        }
        if (productType == null) {
            throw new IllegalArgumentException("productType не должен быть null");
        }

        int count = jdbcTemplate.queryForObject("SELECT COUNT(t.amount) " +
                        "FROM transactions t JOIN products p ON t.PRODUCT_ID = p.ID WHERE t.USER_ID = ? AND p.TYPE = ?",
                Integer.class,
                userId,
                productType);

        boolean result = count != 0;

        return result;
    }

    /**
     * Возвращает сумму всех пополнений (депозитов) по продукту типа productType у пользователя с id userId
     *
     * @param userId      - id пользователя
     * @param productType - тип продукта
     * @return int со значением суммы всех пополнений
     */
    public int getDepositsOfTypeTotal(UUID userId, String productType) {
        logger.info("Входные данные: userId={}, productType={}", userId, productType);
        if (userId == null) {
            throw new IllegalArgumentException("userId не должен быть null");
        }
        if (productType == null) {
            throw new IllegalArgumentException("productType не должен быть null");
        }

        int total = jdbcTemplate.queryForObject("SELECT SUM(t.amount) " +
                        "FROM transactions t JOIN products p ON t.product_id = p.id " +
                        "WHERE t.user_id = ? AND p.type = ? AND t.type = 'DEPOSIT';",
                Integer.class,
                userId,
                productType);

        return total;
    }

    /**
     * Возвращает сумму всех трат (withdraw) по продукту типа productType у пользователя с id userId
     *
     * @param userId      - id пользователя
     * @param productType - тип продукта
     * @return int со значением суммы всех трат
     */
    public int getWithdrawsOfTypeTotal(UUID userId, String productType) {
        logger.info("Входные данные: userId={}, productType={}", userId, productType);
        if (userId == null) {
            throw new IllegalArgumentException("userId не должен быть null");
        }
        if (productType == null) {
            throw new IllegalArgumentException("productType не должен быть null");
        }
        int total = jdbcTemplate.queryForObject("SELECT SUM(t.amount) " +
                        "FROM transactions t JOIN products p ON t.PRODUCT_ID = p.id " +
                        "WHERE t.USER_ID = ? AND p.TYPE = ? AND t.TYPE = 'WITHDRAW'",
                Integer.class,
                userId,
                productType);

        return total;
    }

    // Метод для получения суммы пополнений по продуктам типа DEBIT
    public Double getDepositSumByType(UUID userId, String productType) {
        logger.info("Входные данные: userId={}, productType={}", userId, productType);
        String sql = "SELECT SUM(t.amount) FROM transactions t " +
                "JOIN products p ON t.product_id = p.id " +
                "WHERE t.user_id = ? AND p.type = ? AND t.transactions = 'DEPOSIT'";

        Double sum = jdbcTemplate.queryForObject(sql, new Object[]{userId, productType}, Double.class);
        return sum;
    }

    // Метод для получения суммы трат по продуктам типа DEBIT
    public Double getWithdrawalSumByType(UUID userId, String productType) {
        logger.info("Входные данные: userId={}, productType={}", userId, productType);
        String sql = "SELECT SUM(t.amount) FROM transactions t " +
                "JOIN products p ON t.product_id = p.id " +
                "WHERE t.user_id = ? AND p.type = ? AND t.transactions = 'WITHDRAWAL'";

        Double sum = jdbcTemplate.queryForObject(sql, new Object[]{userId, productType}, Double.class);
        return sum;
    }

    // Метод для получения суммы пополнений по всем продуктам типа SAVING
    public Double getTotalSavingDepositSum(UUID userId) {
        logger.info("Входные данные: userId={}", userId);
        Double sum = getDepositSumByType(userId, "SAVING");
        return sum;
    }

    // Метод для получения суммы пополнений по всем продуктам типа DEBIT
    public Double getTotalDebitDepositSum(UUID userId) {
        logger.info("Входные данные: userId={}", userId);
        Double sum = getDepositSumByType(userId, "DEBIT");
        return sum;
    }

    public int countDebitTransactions(UUID userId) {
        logger.info("Входные данные: userId={}", userId);
        // SQL-запрос для подсчета количества транзакций типа DEBIT для указанного пользователя
        String sql = "SELECT COUNT(*) FROM transactions WHERE user_id = ? AND type = 'DEBIT'";
        return jdbcTemplate.queryForObject(sql, new Object[]{userId}, Integer.class);
    }

    public Double sumDepositTransactions(UUID userId, String type) {
        logger.info("Входные данные: userId={}, type={}", userId, type);
        // SQL-запрос для суммирования всех пополнений (DEPOSIT) для указанного типа транзакции и пользователя
        String sql = "SELECT SUM(amount) FROM transactions WHERE user_id = ? AND type = ? AND operation = 'DEPOSIT'";
        return jdbcTemplate.queryForObject(sql, new Object[]{userId, type}, Double.class);
    }

    public Double sumWithdrawalTransactions(UUID userId, String type) {
        logger.info("Входные данные: userId={}, type={}", userId, type);
        // SQL-запрос для суммирования всех трат (WITHDRAWAL) для указанного типа транзакции и пользователя
        String sql = "SELECT SUM(amount) FROM transactions WHERE user_id = ? AND type = ? AND operation = 'WITHDRAWAL'";
        return jdbcTemplate.queryForObject(sql, new Object[]{userId, type}, Double.class);
    }
}


package bank.recommendationservice.fintech.rulesetimpl;

import bank.recommendationservice.fintech.dto.RecommendationDTO;
import bank.recommendationservice.fintech.interfaces.RecommendationRuleSet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

import static bank.recommendationservice.fintech.RuleSetText.SIMPLE_CREDIT_TEXT;

@Component
public class SimpleCreditRecommendation implements RecommendationRuleSet {

    private final JdbcTemplate jdbcTemplate;

    public SimpleCreditRecommendation(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<RecommendationDTO> getRecommendation(UUID userId) {
        // Проверка наличия кредитного продукта
        String creditSql = "SELECT COUNT(*) FROM products WHERE id = ? AND type = 'CREDIT'";
        int creditCount = jdbcTemplate.queryForObject(creditSql, new Object[]{userId}, Integer.class);

        // Проверка наличия дебетового продукта
        String debitSql = "SELECT COUNT(*) FROM products WHERE id = ? AND type = 'DEBIT'";
        int debitCount = jdbcTemplate.queryForObject(debitSql, new Object[]{userId}, Integer.class);

        // Проверка суммы пополнений по дебетовому счету
        String totalDebitDepositSql = "SELECT SUM(amount) FROM transactions WHERE id = ? AND type = 'DEBIT' AND type = 'DEPOSIT'";
        Double totalDebitDepositSum = jdbcTemplate.queryForObject(totalDebitDepositSql, new Object[]{userId}, Double.class);

        // Проверка суммы снятий по дебетовому счету
        String totalDebitWithdrawalSql = "SELECT SUM(amount) FROM transactions WHERE id = ? AND type = 'DEBIT' AND type = 'WITHDRAWAL'";
        Double totalDebitWithdrawalSum = jdbcTemplate.queryForObject(totalDebitWithdrawalSql, new Object[]{userId}, Double.class);

        // Логика проверки условий
        if (creditCount == 0 && debitCount > 0 && totalDebitDepositSum != null && totalDebitWithdrawalSum != null &&
                totalDebitDepositSum > totalDebitWithdrawalSum && totalDebitWithdrawalSum > 100000) {
            return Optional.of(new RecommendationDTO(UUID.fromString("b3b1e6f4-5a9d-4c8b-b0f2-8b0c9d3e6f6a"),
                    "Простой кредит", SIMPLE_CREDIT_TEXT));
        }
        return Optional.empty();
    }
}


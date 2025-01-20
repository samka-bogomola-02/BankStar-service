package bank.recommendationservice.fintech.rulesetimpl;

import bank.recommendationservice.fintech.dto.RecommendationDTO;
import bank.recommendationservice.fintech.interfaces.RecommendationRuleSet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

import static bank.recommendationservice.fintech.RuleSetText.TOP_SAVING_TEXT;

@Component
public class TopSavingRecommendation implements RecommendationRuleSet {

    private final JdbcTemplate jdbcTemplate;

    public TopSavingRecommendation(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<RecommendationDTO> getRecommendation(UUID userId) {
        // Проверяем, использует ли пользователь хотя бы один продукт типа DEBIT
        String sqlDebitCount = "SELECT COUNT(*) FROM transactions WHERE user_id = ? AND type = 'DEBIT'";
        int debitCount = jdbcTemplate.queryForObject(sqlDebitCount, new Object[]{userId}, Integer.class);

        if (debitCount == 0) {
            return Optional.empty();
        }

        // Сумма пополнений по всем продуктам типа DEBIT
        String sqlDebitSum = "SELECT SUM(amount) FROM transactions WHERE user_id = ? AND type = 'DEBIT' AND operation = 'DEPOSIT'";
        Double debitSum = jdbcTemplate.queryForObject(sqlDebitSum, new Object[]{userId}, Double.class);

        // Сумма пополнений по всем продуктам типа SAVING
        String sqlSavingSum = "SELECT SUM(amount) FROM transactions WHERE user_id = ? AND type = 'SAVING' AND operation = 'DEPOSIT'";
        Double savingSum = jdbcTemplate.queryForObject(sqlSavingSum, new Object[]{userId}, Double.class);

        // Сумма трат по всем продуктам типа DEBIT
        String sqlDebitExpenses = "SELECT SUM(amount) FROM transactions WHERE user_id = ? AND type = 'DEBIT' AND operation = 'WITHDRAWAL'";
        Double debitExpenses = jdbcTemplate.queryForObject(sqlDebitExpenses, new Object[]{userId}, Double.class);

        // Проверка условий для рекомендации
        boolean isDebitSumValid = (debitSum != null && debitSum >= 50000);
        boolean isSavingSumValid = (savingSum != null && savingSum >= 50000);
        boolean isDebitBalanceValid = (debitSum != null && debitExpenses != null && debitSum > debitExpenses);

        if ((isDebitSumValid || isSavingSumValid) && isDebitBalanceValid) {
            return Optional.of(new RecommendationDTO(UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925"),
                    "Откройте свою собственную «Копилку» с нашим банком!", TOP_SAVING_TEXT));
        }

        return Optional.empty();
    }
}


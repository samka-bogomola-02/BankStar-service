package bank.recommendationservice.fintech.rulesetimpl;

import bank.recommendationservice.fintech.dto.RecommendationDTO;
import bank.recommendationservice.fintech.interfaces.RecommendationRuleSet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

import static bank.recommendationservice.fintech.RuleSetText.INVEST_500_TEXT;

@Component
public class Invest500_new implements RecommendationRuleSet {

    private final JdbcTemplate jdbcTemplate;

    public Invest500_new(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Optional<RecommendationDTO> getRecommendation(UUID userId) {
        // Проверка количества дебетовых транзакций
        String sql = "SELECT COUNT(*) FROM transactions WHERE user_id = ? AND type = 'DEBIT'";
        int debitCount = jdbcTemplate.queryForObject(sql, new Object[]{userId}, Integer.class);

        // Проверка количества инвестиционных транзакций
        sql = "SELECT COUNT(*) FROM transactions WHERE user_id = ? AND type = 'INVEST'";
        int investCount = jdbcTemplate.queryForObject(sql, new Object[]{userId}, Integer.class);

        // Получение суммы пополнений по продуктам типа SAVING
        sql = "SELECT SUM(amount) FROM transactions WHERE user_id = ? AND type = 'SAVING' AND type = 'DEPOSIT'";
        Double savingSum = jdbcTemplate.queryForObject(sql, new Object[]{userId}, Double.class);

        // Проверка условий для выдачи рекомендации
        if (debitCount > 0 && investCount == 0 && (savingSum != null && savingSum > 1000)) {
            return Optional.of(new RecommendationDTO(
                    UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"),
                    "Invest 500", INVEST_500_TEXT
            ));
        }
        return Optional.empty();
    }

}

package bank.recommendationservice.fintech.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "DTO для представления счетчика срабатываний динамических правил")
public class RuleStatsDTO {
    @JsonProperty("rule_id")
    private Long ruleId;

    @JsonProperty("count")
    private int count;

    public RuleStatsDTO(Long ruleId, int count) {
        this.ruleId = ruleId;
        this.count = count;
    }
}

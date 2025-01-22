package bank.recommendationservice.fintech.other;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Ответ проверки работоспособности приложения")
@Getter
@Setter
public class HealthCheckResponse {

    @Schema(description = "Статус приложения (UP - работает)")
    private String status;
    @Schema(description = "Имя приложения")
    private String appName;
    @Schema(description = "Версия приложения")
    private String appVersion;

}

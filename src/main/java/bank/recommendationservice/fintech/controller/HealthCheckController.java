package bank.recommendationservice.fintech.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Tag(name = "Health Check", description = "Эндпоинты для проверки работоспособности приложения")
public class HealthCheckController {

    @Value("${spring.application.name}")
    private String appName;

    @Value("${build.version}")
    private String appVersion;

    @GetMapping("/health")
    @Operation(summary = "Проверка работоспособности приложения", description = "Возвращает статус приложения и версию")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Приложение работает",
                    content = @Content(schema = @Schema(implementation = HealthCheckResponse.class)))
    })
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("appName", appName);
        response.put("appVersion", appVersion);
        return ResponseEntity.ok(response);
    }


    @Schema(description = "Ответ проверки работоспособности приложения")
    @Getter
    @Setter
    private static class HealthCheckResponse {
        @Schema(description = "Статус приложения (UP - работает)")
        private String status;
        @Schema(description = "Имя приложения")
        private String appName;
        @Schema(description = "Версия приложения")
        private String appVersion;
    }
}
package bank.recommendationservice.fintech.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management")
@Tag(name = "Management", description = "Эндпоинт для получение названия и версии системы")
public class InfoController {
    private final BuildProperties buildProperties;

    public InfoController(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }


    /**
     * @return строка, содержащая имя и версию приложения
     */
    @GetMapping("/info")
    @Operation(summary = "Получение названия и версии системы", description = "Возвращает название и версию системы")
    public String info() {
        return "name = " + buildProperties.getName() + ", version = " + buildProperties.getVersion();
    }
}


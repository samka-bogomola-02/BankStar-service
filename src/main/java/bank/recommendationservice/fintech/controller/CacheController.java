package bank.recommendationservice.fintech.controller;

import bank.recommendationservice.fintech.service.CacheService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management")
@Tag(name = "Сброс кеша", description = "Эндпоинты для сброса кеша")
public class CacheController {
    private final CacheService cacheService;

    public CacheController(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @PostMapping("/clear-caches")
    public ResponseEntity<String> clearCaches() {
        cacheService.clearCaches();
        return new ResponseEntity<>("Кеш успешно очищен.", HttpStatus.OK);
    }
}

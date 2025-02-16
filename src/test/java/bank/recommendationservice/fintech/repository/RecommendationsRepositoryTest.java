package bank.recommendationservice.fintech.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RecommendationsRepositoryTest {
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private RecommendationsRepository recommendationsRepository;

    private final UUID userId = UUID.randomUUID();
    private final String productType = "testProductType";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test   // Тестирование метода usesProductOfType, когда продукт существует
    public void testUsesProductOfType_WhenExists_ReturnsTrue() {
        // Настройка мока, чтобы он возвращал true при вызове queryForObject
        when(jdbcTemplate.queryForObject(any(String.class), any(Class.class), any(UUID.class), any(String.class)))
                .thenReturn(true);

        boolean result = recommendationsRepository.usesProductOfType(userId, productType);
        // Проверка, что результат соответствует ожидаемому значению true
        assertTrue(result);
        // Проверка, что метод queryForObject был вызван с правильными параметрами
        verify(jdbcTemplate).queryForObject(any(String.class), eq(Boolean.class), eq(userId), eq(productType));
    }

    @Test  // Тестирование метода usesProductOfType, когда продукт не существует
    public void testUsesProductOfType_WhenNotExists_ReturnsFalse() {
        // Настройка мока, чтобы он возвращал false при вызове queryForObject
        when(jdbcTemplate.queryForObject(any(String.class), any(Class.class), any(UUID.class), any(String.class)))
                .thenReturn(false);

        boolean result = recommendationsRepository.usesProductOfType(userId, productType);
        // Проверка, что результат соответствует ожидаемому значению false
        assertFalse(result);
        // Проверка, что метод queryForObject был вызван с правильными параметрами
        verify(jdbcTemplate).queryForObject(any(String.class), eq(Boolean.class), eq(userId), eq(productType));
    }

    @Test  // Тестирование метода getDepositsOfTypeTotal
    public void testGetDepositsOfTypeTotal() {
        Integer expectedSum = 100; // Ожидаемая сумма депозитов
        when(jdbcTemplate.queryForObject(any(String.class), any(Class.class), any(UUID.class), any(String.class)))
                .thenReturn(expectedSum);

        Integer result = recommendationsRepository.getDepositsOfTypeTotal(userId, productType);
        // Проверка, что результат соответствует ожидаемой сумме
        assertEquals(expectedSum, result);
        // Проверка, что метод queryForObject был вызван с правильными параметрами
        verify(jdbcTemplate).queryForObject(any(String.class), eq(Integer.class), eq(userId), eq(productType));
    }

    @Test  // Тестирование метода getWithdrawsOfTypeTotal
    public void testGetWithdrawsOfTypeTotal() {
        Integer expectedSum = 50; // Ожидаемая сумма выводов
        when(jdbcTemplate.queryForObject(any(String.class), any(Class.class), any(UUID.class), any(String.class)))
                .thenReturn(expectedSum);

        Integer result = recommendationsRepository.getWithdrawsOfTypeTotal(userId, productType);
        // Проверка, что результат соответствует ожидаемой сумме
        assertEquals(expectedSum, result);
        // Проверка, что метод queryForObject был вызван с правильными параметрами
        verify(jdbcTemplate).queryForObject(any(String.class), eq(Integer.class), eq(userId), eq(productType));
    }

}
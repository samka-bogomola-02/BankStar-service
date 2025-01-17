package bank.recommendationservice.fintech.testrepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class RecommendationsRepository_PreTestTest {
        // нужно было проверить точно ли это работает 🙂 : bogomolova
        @Mock
        private JdbcTemplate jdbcTemplate;

        @InjectMocks
        private RecommendationsRepository_PreTest recommendationsRepository;

        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        public void testCheckConnection() {
            boolean isConnected = recommendationsRepository.checkConnection();
            assertTrue(isConnected, "Подключение к базе данных должно быть успешным");
        }

        @Test
        public void testGetRandomTransactionAmount_ReturnsAmount() {
            UUID userId = UUID.randomUUID();
            int expectedAmount = 100;

            // Настройка поведения mock-объекта jdbcTemplate
            when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), eq(userId))).thenReturn(expectedAmount);

            // Вызов метода
            int actualAmount = recommendationsRepository.getRandomTransactionAmount(userId);

            // Проверка результата
            assertEquals(expectedAmount, actualAmount);
        }

        @Test
        public void testGetRandomTransactionAmount_ReturnsZero_WhenNoResult() {
            UUID userId = UUID.randomUUID();

            // Настройка поведения mock-объекта jdbcTemplate для возврата null
            when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), eq(userId))).thenReturn(null);

            // Вызов метода
            int actualAmount = recommendationsRepository.getRandomTransactionAmount(userId);

            // Проверка результата
            assertEquals(0, actualAmount);
        }
        @Test public void testGetRandomTransactionAmount() {
            UUID testUser = UUID.randomUUID();
            int amount = recommendationsRepository.getRandomTransactionAmount(testUser);
            System.out.println("Transaction Amount: " + amount);
        }

}
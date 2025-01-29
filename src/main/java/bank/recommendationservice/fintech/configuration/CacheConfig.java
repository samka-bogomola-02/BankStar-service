package bank.recommendationservice.fintech.configuration;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    /**
     * {@code Cache<String, Boolean>} для хранения результатов метода {@code usesProductOfType}.
     * Ключ - строка в формате "userId_productType".
     * Значение - булевое значение, указывающее, использует ли пользователь с {@code userId}
     * продукт типа {@code productType}.
     * Значения в кэше истекают через 60 дней.
     *
     * @return кэш
     */
    @Bean
    public Cache<String, Boolean> productTypeCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(60, TimeUnit.DAYS)
                .build();
    }


    /**
     * {@code Cache<String, Double>} для хранения сумм транзакций по
     * типу продукта и id пользователя.
     * Ключ - строка в формате "userId_productType".
     * Значение - сумма транзакций по типу продукта {@code productType}
     * для пользователя с {@code userId}.
     * Значения в кэше истекают через 60 дней.
     *
     * @return кэш
     */
    @Bean
    public Cache<String, Double> transactionSumCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(60, TimeUnit.DAYS)
                .build();
    }


    /**
     * {@code Cache<String, Integer>} для хранения количества транзакций по
     * типу продукта и id пользователя.
     * Ключ - строка в формате "userId_productType".
     * Значение - количество транзакций по типу продукта {@code productType}
     * для пользователя с {@code userId}.
     * Значения в кэше истекают через 60 дней.
     *
     * @return кэш
     */
    @Bean
    public Cache<String, Integer> transactionCountCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(60, TimeUnit.DAYS)
                .build();
    }
}

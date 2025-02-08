package bank.recommendationservice.fintech.telegrambot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@PropertySource("classpath:tg_token.properties")
public class TelegramBotConfiguration {

    @Value("${bot.token}")
    private String botToken;

    private static final Logger logger = LoggerFactory.getLogger(TelegramBotConfiguration.class);

    @Bean
    public TelegramBot telegramBot() {
        if (botToken == null || botToken.isEmpty()) {
            logger.error("Токен бота не установлен. Проверьте файл конфигурации tg_token.properties");
            throw new IllegalArgumentException("Токен бота не установлен.");
        }
        return new TelegramBot(botToken);
    }
}
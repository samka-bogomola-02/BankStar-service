package bank.recommendationservice.fintech.telegrambot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
public class TelegramBotConfiguration {
    private String botToken;

    private static final Logger logger = LoggerFactory.getLogger(TelegramBotConfiguration.class);

    public TelegramBotConfiguration() {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("tg_token.properties")) {
            if (input == null) {
                logger.error("Не удалось найти файл конфигурации tg_token.properties");
                return;
            }
            properties.load(input);
            this.botToken = properties.getProperty("bot.token");
        } catch (IOException e) {
            logger.error("Не удалось прочитать файл tg_token.properties");
            e.printStackTrace();
        }

    }
    @Bean
    public TelegramBot telegramBot() {
        return new TelegramBot(botToken);
    }

}
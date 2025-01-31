package bank.recommendationservice.fintech.telegrambot.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
public class TelegramBotConfiguration {
    private String botToken;

    public TelegramBotConfiguration() {
        Properties properties = new Properties();
        final Logger logger = LoggerFactory.getLogger(TelegramBotConfiguration.class);
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

    public String getBotToken() {
        return botToken;
    }

}
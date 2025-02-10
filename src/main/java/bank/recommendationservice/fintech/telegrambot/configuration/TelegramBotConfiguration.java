/**
 * Пакет содержит конфигурацию Telegram-бота для сервиса рекомендаций.
 * <p>
 * Этот пакет предназначен для настройки параметров работы Telegram-бота,
 * таких как токен бота и другие необходимые свойства.
 */
package bank.recommendationservice.fintech.telegrambot.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Конфигурация Telegram-бота.
 * <p>
 * Класс отвечает за загрузку токена бота из файла конфигурации
 * {@code tg_token.properties} и предоставление его для использования в приложении.
 * Если файл конфигурации не найден или произошла ошибка чтения, логируется соответствующее сообщение.
 */
@Configuration
public class TelegramBotConfiguration {

    /**
     * Токен Telegram-бота.
     */
    private String botToken;

    /**
     * Конструктор для инициализации конфигурации Telegram-бота.
     * <p>
     * Метод загружает токен бота из файла конфигурации {@code tg_token.properties}.
     * В случае ошибки чтения файла или его отсутствия, логируется ошибка.
     */
    public TelegramBotConfiguration() {
        Properties properties = new Properties();
        final Logger logger = LoggerFactory.getLogger(TelegramBotConfiguration.class);

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("tg_token.properties")) {
            if (input == null) {
                // Логирование ошибки, если файл не найден
                logger.error("Не удалось найти файл конфигурации tg_token.properties");
                return;
            }

            // Загрузка свойств из файла
            properties.load(input);
            this.botToken = properties.getProperty("bot.token");

        } catch (IOException e) {
            // Логирование ошибки, если произошла проблема с чтением файла
            logger.error("Не удалось прочитать файл tg_token.properties");
            e.printStackTrace();
        }
    }

    /**
     * Возвращает токен Telegram-бота.
     *
     * @return Строка, содержащая токен бота.
     */
    public String getBotToken() {
        return botToken;
    }
}
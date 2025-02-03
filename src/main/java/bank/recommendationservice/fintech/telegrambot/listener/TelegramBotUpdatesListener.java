package bank.recommendationservice.fintech.telegrambot.listener;

import bank.recommendationservice.fintech.dto.RecommendationDTO;
import bank.recommendationservice.fintech.service.RecommendationService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TelegramBotUpdatesListener implements UpdatesListener {
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    TelegramBot telegramBot;
    @Autowired
    RecommendationService recommendationService;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);

            if (update.message() != null) {
                Message message = update.message();
                String text = message.text();
                long chatId = message.chat().id();

                if ("/start".equals(text)) {
                    String welcomeMessage = "Привет! Я Star Bank Assistant. Используйте команду /recommend <имя_пользователя> для получения рекомендаций.";
                    SendMessage sendMessage = new SendMessage(chatId, welcomeMessage);
                    telegramBot.execute(sendMessage);
                } else if (text.startsWith("/recommend")) {
                    String[] commandParts = text.split(" ");
                    if (commandParts.length > 1) {
                        String username = commandParts[1]; // Получаем имя пользователя из команды
                        handleRecommendationRequest(chatId, username);
                    } else {
                        SendMessage sendMessage = new SendMessage(chatId, "Пожалуйста, укажите имя пользователя после команды /recommend.");
                        telegramBot.execute(sendMessage);
                    }
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;


    }

    private void handleRecommendationRequest(long chatId, String username) {
        List<RecommendationDTO> response = recommendationService.getRecommendations(username);
        SendMessage sendMessage = new SendMessage(chatId, response.toString());
        telegramBot.execute(sendMessage);
    }
}
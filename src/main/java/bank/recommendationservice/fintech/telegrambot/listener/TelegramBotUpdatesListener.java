package bank.recommendationservice.fintech.telegrambot.listener;

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

                if ("/start".equals(text)) {
                    long chatId = message.chat().id();
                    String welcomeMessage = "Привет! Я Star Bank Assistant"; // проверка ответа
                    SendMessage sendMessage = new SendMessage(chatId, welcomeMessage);
                    telegramBot.execute(sendMessage);
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
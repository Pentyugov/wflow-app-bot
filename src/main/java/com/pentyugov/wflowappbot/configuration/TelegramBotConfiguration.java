package com.pentyugov.wflowappbot.configuration;

import com.pentyugov.wflowappbot.application.bot.Bot;
import com.pentyugov.wflowappbot.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class TelegramBotConfiguration implements ApplicationListener<ContextRefreshedEvent> {
    private static final Long CHAT_ID = 438939028L;

    private final Bot bot;
    private final UserService userService;

    @Autowired
    public TelegramBotConfiguration(Bot bot, UserService userService) {
        this.bot = bot;
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(bot);

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(CHAT_ID.toString());
            sendMessage.setText("Test message from spring boot application");
            bot.sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

        userService.loadLoggedUsers();
    }
}

package com.pentyugov.wflow.telbot.application.bot;

import com.pentyugov.wflow.telbot.application.bot.commands.*;
import com.pentyugov.wflow.telbot.application.bot.handler.MessageHandler;
import com.pentyugov.wflow.telbot.application.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class TelBot extends TelegramLongPollingCommandBot {
    public static final String NAME = "telbot";
    @Value("${telegram.bot.name}")
    private String botName;
    @Value("${telegram.bot.token}")
    private String botToken;

    @Autowired
    private MessageHandler messageHandler;
    @Autowired
    private SessionService sessionService;

    public TelBot(StartCommand startCommand, CodeCommand codeCommand, LoginCommand loginCommand, LogoutCommand logoutCommand, HelpCommand helpCommand) {
        registerAll(startCommand, codeCommand, loginCommand, logoutCommand, helpCommand);
    }


    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        onHandleNonCommandUpdate(update);
    }

    @Override
    public String getBotToken() {
        return this.botToken;
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        if (sessionService.isConnectedToServer()) {
            super.onUpdatesReceived(updates);
        } else {
            sendConnectionRefusedMessage(updates.get(0).getMessage().getChat());
        }
    }

    private void onHandleNonCommandUpdate(Update update) {
        if (update.hasMessage()) {
            sendMessage(messageHandler.getAnswerMessage(update.getMessage()));
        } else if (update.hasCallbackQuery()){
            onHandleCallbackQuery(update.getCallbackQuery());
        }
    }

    private void onHandleCallbackQuery(CallbackQuery callbackQuery) {
        SendMessage sendMessage = messageHandler.handleCallbackQuery(callbackQuery);
        if (sendMessage != null)
            sendMessage(sendMessage);
    }

    public void sendMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendConnectionRefusedMessage(Chat chat) {
        sendMessage(messageHandler.getNotConnectedToServerMessage(chat));
    }

    public void sendStartMessage(User user, Chat chat) {
        sendMessage(messageHandler.getStartMessage(user, chat));
    }
}

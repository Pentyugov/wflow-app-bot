package com.pentyugov.wflowappbot.application.bot;

import com.pentyugov.wflowappbot.application.bot.commands.*;
import com.pentyugov.wflowappbot.application.bot.handler.MessageHandler;
import com.pentyugov.wflowappbot.application.service.FeignTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class Bot extends TelegramLongPollingCommandBot {
    @Value("${telegram.bot.name}")
    private String botName;
    @Value("${telegram.bot.token}")
    private String botToken;

    private final StartCommand startCommand;
    private final LoginCommand loginCommand;
    private final CodeCommand codeCommand;
    private final MessageHandler messageHandler;
    private final HelpCommand helpCommand;
    private final LogoutCommand logoutCommand;

    @Autowired
    public Bot(StartCommand startCommand, LoginCommand loginCommand, CodeCommand codeCommand, MessageHandler messageHandler, HelpCommand helpCommand, LogoutCommand logoutCommand) {
        super();
        this.startCommand = startCommand;
        this.loginCommand = loginCommand;
        this.codeCommand = codeCommand;
        this.messageHandler = messageHandler;
        this.helpCommand = helpCommand;
        this.logoutCommand = logoutCommand;

        registerAll(this.startCommand, this.loginCommand, this.codeCommand, this.helpCommand, this.logoutCommand);
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
        super.onUpdatesReceived(updates);
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
}

package com.pentyugov.wflowappbot.application.bot;

import com.pentyugov.wflowappbot.application.bot.commands.CodeCommand;
import com.pentyugov.wflowappbot.application.bot.commands.LoginCommand;
import com.pentyugov.wflowappbot.application.bot.commands.StartCommand;
import com.pentyugov.wflowappbot.application.bot.handler.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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

    @Autowired
    public Bot(StartCommand startCommand, LoginCommand loginCommand, CodeCommand codeCommand, MessageHandler messageHandler) {
        super();
        this.startCommand = startCommand;
        this.loginCommand = loginCommand;
        this.codeCommand = codeCommand;
        this.messageHandler = messageHandler;

        registerAll(this.startCommand, this.loginCommand, this.codeCommand);
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

    public void onHandleNonCommandUpdate(Update update) {
        if (update.hasMessage()) {
            sendMessage(messageHandler.getAnswerMessage(update.getMessage()));
        }
    }

    public void sendMessage(SendMessage sendMessage) {

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}

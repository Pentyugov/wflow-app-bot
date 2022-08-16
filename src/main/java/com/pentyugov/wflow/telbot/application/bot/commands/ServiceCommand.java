package com.pentyugov.wflow.telbot.application.bot.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

abstract class ServiceCommand extends BotCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceCommand.class);


    ServiceCommand(String identifier, String description) {
        super(identifier, description);
    }

    /**
     * Отправка ответа пользователю
     */
    void sendAnswer(AbsSender absSender, SendMessage sendMessage) {

        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            LOGGER.error(String.format("Ошибка %s. Чат %s.", e.getMessage(), sendMessage.getChatId()));
            e.printStackTrace();
        }
    }
}

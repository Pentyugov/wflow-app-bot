package com.pentyugov.wflow.telbot.application.bot.commands;

import com.pentyugov.wflow.telbot.application.bot.handler.MessageMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class StartCommand extends ServiceCommand {

    @Autowired
    private MessageMaker messageMaker;

    public StartCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        sendAnswer(absSender, messageMaker.createStartMessage(user, chat));
    }
}

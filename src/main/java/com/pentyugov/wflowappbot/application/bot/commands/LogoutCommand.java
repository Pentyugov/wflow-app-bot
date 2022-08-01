package com.pentyugov.wflowappbot.application.bot.commands;

import com.pentyugov.wflowappbot.application.bot.MessageMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class LogoutCommand extends ServiceCommand {

    @Autowired
    private MessageMaker messageMaker;

    public LogoutCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        sendAnswer(absSender, messageMaker.createMessage(user, chat, "Разлогирование...."));
    }
}
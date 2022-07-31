package com.pentyugov.wflowappbot.application.service.impl;

import com.pentyugov.wflowappbot.application.bot.Bot;
import com.pentyugov.wflowappbot.application.bot.MessageMaker;
import com.pentyugov.wflowappbot.application.rest.payload.request.TelegramOverdueTasksRequest;
import com.pentyugov.wflowappbot.application.service.MessageService;
import com.pentyugov.wflowappbot.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(MessageService.NAME)
public class MessageServiceImpl implements MessageService {

    private final Bot bot;
    private final MessageMaker messageMaker;
    private final UserService userService;

    @Autowired
    public MessageServiceImpl(Bot bot, MessageMaker messageMaker, UserService userService) {
        this.bot = bot;
        this.messageMaker = messageMaker;
        this.userService = userService;
    }

    @Override
    public void sendTaskOverdueMessage(TelegramOverdueTasksRequest request) {
        if (userService.isUserLoggedIn(request.getTelUserId())) {
            bot.sendMessage(messageMaker.creatTaskOverdueMessage(request.getTelChatId(), request.getTask()));
        }
    }
}

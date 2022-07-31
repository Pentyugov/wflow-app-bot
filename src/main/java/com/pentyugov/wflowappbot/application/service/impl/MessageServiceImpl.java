package com.pentyugov.wflowappbot.application.service.impl;

import com.pentyugov.wflowappbot.application.bot.Bot;
import com.pentyugov.wflowappbot.application.bot.MessageMaker;
import com.pentyugov.wflowappbot.application.rest.payload.request.TelegramTaskSendMessageRequest;
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
    public void sendTaskMessage(TelegramTaskSendMessageRequest request) {
        if (request.getType().equals(TelegramTaskSendMessageRequest.TYPE_ASSIGNED)) {
            sendTaskAssignedMessage(request);
        } else if (request.getType().equals(TelegramTaskSendMessageRequest.TYPE_OVERDUE)) {
            sendTaskOverdueMessage(request);
        }
    }

    private void sendTaskAssignedMessage(TelegramTaskSendMessageRequest request) {
        if (userService.isUserLoggedIn(request.getTelUserId())) {
            bot.sendMessage(messageMaker.creatTaskAssignedMessage(request.getTelChatId(), request.getTask()));
        }
    }


    private void sendTaskOverdueMessage(TelegramTaskSendMessageRequest request) {
        if (userService.isUserLoggedIn(request.getTelUserId())) {
            bot.sendMessage(messageMaker.creatTaskOverdueMessage(request.getTelChatId(), request.getTask()));
        }
    }

}

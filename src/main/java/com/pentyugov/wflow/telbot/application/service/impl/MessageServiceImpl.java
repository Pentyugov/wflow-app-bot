package com.pentyugov.wflow.telbot.application.service.impl;

import com.pentyugov.wflow.telbot.application.bot.TelBot;
import com.pentyugov.wflow.telbot.application.bot.handler.MessageMaker;
import com.pentyugov.wflow.telbot.application.service.MessageService;
import com.pentyugov.wflow.telbot.application.service.UserService;
import com.pentyugov.wflow.telbot.application.web.rest.payload.request.TelbotTaskSendMessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(MessageService.NAME)
public class MessageServiceImpl implements MessageService {

    private final TelBot telBot;
    private final MessageMaker messageMaker;
    private final UserService userService;

    @Autowired
    public MessageServiceImpl(TelBot bot, MessageMaker messageMaker, UserService userService) {
        this.telBot = bot;
        this.messageMaker = messageMaker;
        this.userService = userService;
    }

    @Override
    public void sendTaskMessage(TelbotTaskSendMessageRequest request) {
        if (request.getType().equals(TelbotTaskSendMessageRequest.TYPE_ASSIGNED)) {
            sendTaskAssignedMessage(request);
        } else if (request.getType().equals(TelbotTaskSendMessageRequest.TYPE_OVERDUE)) {
            sendTaskOverdueMessage(request);
        }
    }

    private void sendTaskAssignedMessage(TelbotTaskSendMessageRequest request) {
        if (userService.isUserLoggedIn(request.getTelUserId())) {
            telBot.sendMessage(messageMaker.creatTaskAssignedMessage(request.getTelChatId(), request.getTask()));
        }
    }


    private void sendTaskOverdueMessage(TelbotTaskSendMessageRequest request) {
        if (userService.isUserLoggedIn(request.getTelUserId())) {
            telBot.sendMessage(messageMaker.creatTaskOverdueMessage(request.getTelChatId(), request.getTask()));
        }
    }

}

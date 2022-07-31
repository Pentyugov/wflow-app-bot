package com.pentyugov.wflowappbot.application.service;

import com.pentyugov.wflowappbot.application.rest.payload.request.TelegramTaskSendMessageRequest;

public interface MessageService {
    String NAME = "tel$MessageService";

    void sendTaskMessage(TelegramTaskSendMessageRequest request);
}

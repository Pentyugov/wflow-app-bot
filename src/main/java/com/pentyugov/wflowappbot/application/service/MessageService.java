package com.pentyugov.wflowappbot.application.service;

import com.pentyugov.wflowappbot.application.rest.payload.request.TelegramOverdueTasksRequest;

public interface MessageService {
    String NAME = "tel$MessageService";

    void sendTaskOverdueMessage(TelegramOverdueTasksRequest request);
}

package com.pentyugov.wflowappbot.application.service;

import com.pentyugov.wflowappbot.application.rest.payload.request.TelbotTaskSendMessageRequest;

public interface MessageService {
    String NAME = "tel$MessageService";

    void sendTaskMessage(TelbotTaskSendMessageRequest request);
}

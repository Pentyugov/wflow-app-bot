package com.pentyugov.wflow.telbot.application.service;

import com.pentyugov.wflow.telbot.application.web.rest.payload.request.TelbotTaskSendMessageRequest;

public interface MessageService {
    String NAME = "tel$MessageService";

    void sendTaskMessage(TelbotTaskSendMessageRequest request);
}

package com.pentyugov.wflow.telbot.application.web.rest.controller;

import com.pentyugov.wflow.telbot.application.service.MessageService;
import com.pentyugov.wflow.telbot.application.web.rest.payload.request.TelbotTaskSendMessageRequest;
import com.pentyugov.wflow.telbot.application.web.rest.payload.response.TelbotTaskSendMessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final MessageService messageService;

    @Autowired
    public TaskController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/send")
    public ResponseEntity<TelbotTaskSendMessageResponse> receiveOverdueTasks(@RequestBody TelbotTaskSendMessageRequest request) {
        messageService.sendTaskMessage(request);
        TelbotTaskSendMessageResponse response = new TelbotTaskSendMessageResponse();
        response.setHttpStatus(HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

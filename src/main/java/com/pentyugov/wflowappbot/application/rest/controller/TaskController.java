package com.pentyugov.wflowappbot.application.rest.controller;

import com.pentyugov.wflowappbot.application.rest.payload.request.TelegramOverdueTasksRequest;
import com.pentyugov.wflowappbot.application.rest.payload.response.TelegramOverdueTasksResponse;
import com.pentyugov.wflowappbot.application.service.MessageService;
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

    @PostMapping("/overdue")
    public ResponseEntity<TelegramOverdueTasksResponse> receiveOverdueTasks(@RequestBody TelegramOverdueTasksRequest request) {
        messageService.sendTaskOverdueMessage(request);
        TelegramOverdueTasksResponse response = new TelegramOverdueTasksResponse();
        response.setHttpStatus(HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

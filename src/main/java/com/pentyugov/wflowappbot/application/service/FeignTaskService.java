package com.pentyugov.wflowappbot.application.service;

import com.pentyugov.wflowappbot.application.rest.payload.request.TelegramGetTaskPageRequest;
import com.pentyugov.wflowappbot.application.rest.payload.response.TelegramGetTaskPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "taskService", url = "http://localhost:8080/api/v1/telegram")
public interface FeignTaskService {

    @PostMapping("/tasks/page")
    TelegramGetTaskPageResponse getTaskPage(@RequestBody TelegramGetTaskPageRequest request);

}

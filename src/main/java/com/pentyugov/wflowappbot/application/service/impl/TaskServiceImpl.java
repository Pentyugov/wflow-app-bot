package com.pentyugov.wflowappbot.application.service.impl;

import com.pentyugov.wflowappbot.application.ApplicationConstants;
import com.pentyugov.wflowappbot.application.model.WflowTask;
import com.pentyugov.wflowappbot.application.rest.payload.request.TelegramGetTaskPageRequest;
import com.pentyugov.wflowappbot.application.rest.payload.response.TelegramGetTaskPageResponse;
import com.pentyugov.wflowappbot.application.service.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(TaskService.NAME)
public class TaskServiceImpl implements TaskService {

    Map<User, TelegramGetTaskPageResponse> loadedUserTaskPages = new HashMap<>();

    @Override
    public List<WflowTask> getTaskPage(User user, Integer page) {

        RestTemplate restTemplate = new RestTemplate();
        TelegramGetTaskPageRequest request = TelegramGetTaskPageRequest.builder()
                .page(0)
                .sortBy("createDate")
                .telUserId(user.getId())
                .build();

        TelegramGetTaskPageResponse response = restTemplate.postForObject(ApplicationConstants.API_GET_TASKS_PAGE_ENDPOINT, request, TelegramGetTaskPageResponse.class);
        loadedUserTaskPages.put(user, response);

        return response.getTasks();
    }

}

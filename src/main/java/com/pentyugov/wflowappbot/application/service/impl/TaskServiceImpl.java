package com.pentyugov.wflowappbot.application.service.impl;

import com.pentyugov.wflowappbot.application.ApplicationConstants;
import com.pentyugov.wflowappbot.application.model.WflowTask;
import com.pentyugov.wflowappbot.application.rest.payload.request.TelegramGetTaskPageRequest;
import com.pentyugov.wflowappbot.application.rest.payload.response.TelegramGetTaskPageResponse;
import com.pentyugov.wflowappbot.application.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(TaskService.NAME)
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final RestTemplate restTemplate;

    Map<User, TelegramGetTaskPageResponse> loadedUserTaskPages = new HashMap<>();

    @Override
    public List<WflowTask> getTaskPage(User user, Integer page) {
        TelegramGetTaskPageRequest request = TelegramGetTaskPageRequest.builder()
                .page(page)
                .sortBy("createDate")
                .telUserId(user.getId())
                .build();

        TelegramGetTaskPageResponse response = restTemplate.postForObject(ApplicationConstants.API_GET_TASKS_PAGE_ENDPOINT, request, TelegramGetTaskPageResponse.class);
        loadedUserTaskPages.put(user, response);
        return response.getTasks();

    }

    @Override
    public List<WflowTask> getNextTaskPage(User user) {
        int nextPage = 0;
        if (loadedUserTaskPages.get(user) != null) {
            Integer currentPage = loadedUserTaskPages.get(user).getPage();
            if (currentPage >= 0)
                nextPage = currentPage + 1;
        }
        return getTaskPage(user, nextPage);
    }

    @Override
    public List<WflowTask> getPrevTaskPage(User user) {
        int prevPage = 0;
        if (loadedUserTaskPages.get(user) != null) {
            Integer currentPage = loadedUserTaskPages.get(user).getPage();
            if (currentPage > 0)
                prevPage = currentPage - 1;
        }
        return getTaskPage(user, prevPage);
    }

    @Override
    public Integer getCurrentPageNumber(User user) {
        TelegramGetTaskPageResponse response = loadedUserTaskPages.get(user);
        if (response != null)
            return response.getPage();
        return 0;
    }

}

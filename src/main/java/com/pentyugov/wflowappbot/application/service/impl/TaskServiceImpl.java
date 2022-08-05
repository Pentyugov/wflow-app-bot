package com.pentyugov.wflowappbot.application.service.impl;

import com.pentyugov.wflowappbot.application.ApplicationConstants;
import com.pentyugov.wflowappbot.application.model.WflowTask;
import com.pentyugov.wflowappbot.application.rest.payload.request.TelegramGetTaskPageRequest;
import com.pentyugov.wflowappbot.application.rest.payload.response.TelegramGetTaskPageResponse;
import com.pentyugov.wflowappbot.application.service.SessionService;
import com.pentyugov.wflowappbot.application.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(TaskService.NAME)
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    private final RestTemplate restTemplate;
    private final SessionService sessionService;

    Map<User, TelegramGetTaskPageResponse> loadedUserTaskPages = new HashMap<>();

    @Override
    public List<WflowTask> getTaskPage(User user, Integer page) {

        List<WflowTask> result = Collections.emptyList();
        TelegramGetTaskPageRequest request = TelegramGetTaskPageRequest.builder()
                .page(page)
                .sortBy("createDate")
                .telUserId(user.getId())
                .build();

        try {
            ResponseEntity<TelegramGetTaskPageResponse> response = restTemplate.exchange(
                    ApplicationConstants.API_GET_TASKS_PAGE_ENDPOINT,
                    HttpMethod.POST,
                    new HttpEntity<>(request, sessionService.getJwtHeaders()),
                    TelegramGetTaskPageResponse.class,
                    Collections.emptyMap()
            );

            if (response.getStatusCode().equals(HttpStatus.OK) && response.getBody() != null) {
                result = response.getBody().getTasks();
                loadedUserTaskPages.put(user, response.getBody());
            }
        } catch (HttpStatusCodeException e) {
            logger.error(e.getMessage());
        }

        return result;
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
    public WflowTask getTaskById(User user, String taskId) {
        WflowTask wflowTask = null;
        try {
            ResponseEntity<WflowTask> response = restTemplate.exchange(
                    String.format(ApplicationConstants.API_GET_TASKS_BY_ID_ENDPOINT, taskId),
                    HttpMethod.GET,
                    new HttpEntity<>(null, sessionService.getJwtHeaders()),
                    WflowTask.class,
                    Collections.emptyMap()
            );

            if (response.getStatusCode().equals(HttpStatus.OK) && response.getBody() != null) {
                wflowTask = response.getBody();
            }
        } catch (HttpStatusCodeException e) {
            logger.error(e.getMessage());
        }

        return wflowTask;
    }

    @Override
    public Integer getCurrentPageNumber(User user) {
        TelegramGetTaskPageResponse response = loadedUserTaskPages.get(user);
        if (response != null)
            return response.getPage();
        return 0;
    }

}

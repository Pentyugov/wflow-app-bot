package com.pentyugov.wflow.telbot.application.service.impl;

import com.pentyugov.wflow.telbot.application.model.WflowTask;
import com.pentyugov.wflow.telbot.application.service.SessionService;
import com.pentyugov.wflow.telbot.application.service.TaskService;
import com.pentyugov.wflow.telbot.application.service.feign.FeignTaskService;
import com.pentyugov.wflow.telbot.application.system.ApplicationConstants;
import com.pentyugov.wflow.telbot.application.web.rest.payload.request.TelbotGetTaskPageRequest;
import com.pentyugov.wflow.telbot.application.web.rest.payload.response.TelbotGetTaskPageResponse;
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
    private final FeignTaskService feignTaskService;

    private final Map<User, TelbotGetTaskPageResponse> loadedUserTaskPages = new HashMap<>();

    @Override
    public List<WflowTask> getTaskPage(User user, Integer page) {
        List<WflowTask> result = Collections.emptyList();
        TelbotGetTaskPageRequest request = TelbotGetTaskPageRequest.builder()
                .page(page)
                .sortBy("createDate")
                .telUserId(user.getId())
                .build();

        try {
            ResponseEntity<TelbotGetTaskPageResponse> response = feignTaskService.getTaskPage(sessionService.getJwtHeaders(), request);

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
        TelbotGetTaskPageResponse response = loadedUserTaskPages.get(user);
        if (response != null)
            return response.getPage();
        return 0;
    }

}

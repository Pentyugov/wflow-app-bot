package com.pentyugov.wflowappbot.application.service.impl;

import com.pentyugov.wflowappbot.application.model.WflowTask;
import com.pentyugov.wflowappbot.application.service.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(TaskService.NAME)
public class TaskServiceImpl implements TaskService {
    @Override
    public List<WflowTask> getTaskPage(Integer page) {
        return null;
    }

//    private List<WflowTask> loadTaskPage(Integer page) {
//        RestTemplate restTemplate = new RestTemplate();
//        Map<String, Object> map = new HashMap<>();
//        map.put("page", page);
//        map.put("")
//        restTemplate.get
//    }
}

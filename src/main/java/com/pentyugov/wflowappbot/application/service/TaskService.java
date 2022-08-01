package com.pentyugov.wflowappbot.application.service;

import com.pentyugov.wflowappbot.application.model.WflowTask;

import java.util.List;

public interface TaskService {
    String NAME = "tel$TaskService";

    List<WflowTask> getTaskPage(Integer page);
}

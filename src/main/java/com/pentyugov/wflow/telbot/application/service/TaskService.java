package com.pentyugov.wflow.telbot.application.service;

import com.pentyugov.wflow.telbot.application.model.WflowTask;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.List;

public interface TaskService {
    String NAME = "tel$TaskService";

    List<WflowTask> getTaskPage(User user, Integer page);
    List<WflowTask> getNextTaskPage(User user);
    List<WflowTask> getPrevTaskPage(User user);
    WflowTask getTaskById(User user, String taskId);
    Integer getCurrentPageNumber(User user);
}

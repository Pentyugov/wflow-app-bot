package com.pentyugov.wflowappbot.application.service;

import com.pentyugov.wflowappbot.application.model.WflowTask;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.List;

public interface TaskService {
    String NAME = "tel$TaskService";

    List<WflowTask> getTaskPage(User user, Integer page);
}

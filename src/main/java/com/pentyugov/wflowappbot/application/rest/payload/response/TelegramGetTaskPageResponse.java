package com.pentyugov.wflowappbot.application.rest.payload.response;

import com.pentyugov.wflowappbot.application.model.WflowTask;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TelegramGetTaskPageResponse {

    private Integer page;
    List<WflowTask> tasks;
}


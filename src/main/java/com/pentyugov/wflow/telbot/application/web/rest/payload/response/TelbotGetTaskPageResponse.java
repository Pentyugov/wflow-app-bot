package com.pentyugov.wflow.telbot.application.web.rest.payload.response;

import com.pentyugov.wflow.telbot.application.model.WflowTask;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TelbotGetTaskPageResponse {

    private Integer page;
    List<WflowTask> tasks;
}

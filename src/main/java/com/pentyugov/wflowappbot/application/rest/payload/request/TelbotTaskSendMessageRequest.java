package com.pentyugov.wflowappbot.application.rest.payload.request;

import com.pentyugov.wflowappbot.application.model.WflowTask;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TelbotTaskSendMessageRequest {

    public final static Integer TYPE_ASSIGNED = 10;
    public final static Integer TYPE_OVERDUE = 30;

    private Integer type;
    private Long telUserId;
    private Long telChatId;
    private WflowTask task;

}

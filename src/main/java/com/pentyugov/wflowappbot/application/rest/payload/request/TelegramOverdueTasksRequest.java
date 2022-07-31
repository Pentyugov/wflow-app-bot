package com.pentyugov.wflowappbot.application.rest.payload.request;

import com.pentyugov.wflowappbot.application.model.WflowTask;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TelegramOverdueTasksRequest {

    private Long telUserId;
    private Long telChatId;
    private WflowTask task;

}

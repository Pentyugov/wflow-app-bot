package com.pentyugov.wflow.telbot.application.web.rest.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TelbotLoginUserRequest {

    private String username;
    private Long telUserId;
    private Long telChatId;
}

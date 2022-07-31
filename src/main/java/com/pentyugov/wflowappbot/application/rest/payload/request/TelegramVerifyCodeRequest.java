package com.pentyugov.wflowappbot.application.rest.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TelegramVerifyCodeRequest {

    private Long telUserId;
    private String code;
}

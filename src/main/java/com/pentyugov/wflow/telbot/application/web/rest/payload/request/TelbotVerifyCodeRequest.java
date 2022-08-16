package com.pentyugov.wflow.telbot.application.web.rest.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TelbotVerifyCodeRequest {

    private Long telUserId;
    private String code;
}

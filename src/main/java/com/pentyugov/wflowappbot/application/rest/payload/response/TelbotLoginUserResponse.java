package com.pentyugov.wflowappbot.application.rest.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class TelbotLoginUserResponse {

    private String userId;
    private HttpStatus status;
}

package com.pentyugov.wflowappbot.application.rest.payload.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {

    private String username;
    private String password;

}

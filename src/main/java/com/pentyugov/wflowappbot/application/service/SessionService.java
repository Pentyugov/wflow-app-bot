package com.pentyugov.wflowappbot.application.service;

import org.springframework.http.HttpHeaders;

public interface SessionService {
    String NAME = "tel$SessionService";

    void authenticate();
    Boolean isConnectedToServer();
    HttpHeaders getJwtHeaders();
    Boolean checkConnection();
    void setSessionId(String sessionId);

}

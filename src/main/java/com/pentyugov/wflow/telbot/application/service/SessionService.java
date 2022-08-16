package com.pentyugov.wflow.telbot.application.service;

import org.springframework.http.HttpHeaders;

public interface SessionService {
    String NAME = "tel$SessionService";

    void authenticate();
    Boolean isConnectedToServer();
    HttpHeaders getJwtHeaders();
    Boolean checkConnection();
    void setSessionId(String sessionId);

}

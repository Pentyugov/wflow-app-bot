package com.pentyugov.wflowappbot.application.service;

public interface SessionService {
    String NAME = "tel$SessionService";

    void authenticate();

    Boolean isConnectedToServer();

    String getAuthToken();

}

package com.pentyugov.wflowappbot.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConstants {
    public static Integer TASKS_PER_PAGE = 10;

    public static String API_URL;
    public static String API_CHECK_CONNECTION;
    public static String API_AUTH_URL;
    public static String API_LOGIN_ENDPOINT;
    public static String API_GET_LOGGED_USERS_ENDPOINT;
    public static String API_VERIFY_CODE_ENDPOINT;
    public static String API_GET_TASKS_BY_ID_ENDPOINT;
    public static String API_GET_TASKS_PAGE_ENDPOINT;

    @Value("${server.api.url}")
    public void setApiUrl(String url) {
        ApplicationConstants.API_URL = url;
        ApplicationConstants.API_AUTH_URL                   = ApplicationConstants.API_URL + "/auth/login";
        ApplicationConstants.API_CHECK_CONNECTION           = ApplicationConstants.API_URL + "/v1/telbot/check-connection";
        ApplicationConstants.API_LOGIN_ENDPOINT             = ApplicationConstants.API_URL + "/v1/telbot/login";
        ApplicationConstants.API_GET_LOGGED_USERS_ENDPOINT  = ApplicationConstants.API_URL + "/v1/telbot/logged-users";
        ApplicationConstants.API_VERIFY_CODE_ENDPOINT       = ApplicationConstants.API_URL + "/v1/telbot/verify-code";
        ApplicationConstants.API_GET_TASKS_BY_ID_ENDPOINT   = ApplicationConstants.API_URL + "/v1/telbot/tasks/%s";
        ApplicationConstants.API_GET_TASKS_PAGE_ENDPOINT    = ApplicationConstants.API_URL + "/v1/telbot/tasks/page";
    }

    public interface Security {
        String JWT_TOKEN_HEADER = "Jwt-Token";
    }

}

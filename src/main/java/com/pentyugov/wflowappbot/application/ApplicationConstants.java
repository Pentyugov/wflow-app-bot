package com.pentyugov.wflowappbot.application;

import org.springframework.beans.factory.annotation.Value;

public class ApplicationConstants {

    public interface Security {
        String JWT_TOKEN_HEADER = "Jwt-Token";
    }

    @Value("${server.api.url}")
    public static String API_URL;
    public static String API_AUTH_URL = API_URL + "/auth/login";
    public static String API_LOGIN_ENDPOINT = API_URL + "/v1/telegram/login";
    public static String API_GET_LOGGED_USERS_ENDPOINT = API_URL + "/v1/telegram/logged-users";
    public static String API_VERIFY_CODE_ENDPOINT = API_URL + "/v1/telegram/verify-code";
    public static String API_GET_TASKS_PAGE_ENDPOINT = API_URL + "/v1/telegram/tasks/page";

    public static Integer TASKS_PER_PAGE = 10;

}

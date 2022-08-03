package com.pentyugov.wflowappbot.application;

public class ApplicationConstants {

    public static String API_URL = "http://localhost:8080/api/v1/telegram";
    public static String API_LOGIN_ENDPOINT = API_URL + "/login";
    public static String API_GET_LOGGED_USERS_ENDPOINT = API_URL + "/logged-users";
    public static String API_VERIFY_CODE_ENDPOINT = API_URL + "/verify-code";
    public static String API_GET_TASKS_PAGE_ENDPOINT = API_URL + "/tasks/page";

    public static Integer TASKS_PER_PAGE = 10;

}

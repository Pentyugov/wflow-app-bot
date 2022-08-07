package com.pentyugov.wflowappbot.application.service.impl;


import static com.pentyugov.wflowappbot.application.bot.keyboard.InlineKeyboardConstants.CallbackQueryAction.*;
import com.pentyugov.wflowappbot.application.ApplicationConstants;
import com.pentyugov.wflowappbot.application.bot.BotMessageEnum;
import com.pentyugov.wflowappbot.application.model.UserSettings;
import com.pentyugov.wflowappbot.application.model.WflowUser;
import com.pentyugov.wflowappbot.application.rest.payload.LoginException;
import com.pentyugov.wflowappbot.application.rest.payload.request.TelbotLoginUserRequest;
import com.pentyugov.wflowappbot.application.rest.payload.request.TelbotUpdateUserSettingsRequest;
import com.pentyugov.wflowappbot.application.rest.payload.request.TelbotVerifyCodeRequest;
import com.pentyugov.wflowappbot.application.rest.payload.response.TelbotLoggedUsersResponse;
import com.pentyugov.wflowappbot.application.rest.payload.response.TelbotLoginUserResponse;
import com.pentyugov.wflowappbot.application.rest.payload.response.TelbotVerifyCodeResponse;
import com.pentyugov.wflowappbot.application.service.SessionService;
import com.pentyugov.wflowappbot.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.*;

@Service(UserService.NAME)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private List<WflowUser> users = new ArrayList<>();
    private final SessionService sessionService;
    private final RestTemplate restTemplate;

    @Override
    public void loadLoggedUsers() {
        try {
            ResponseEntity<TelbotLoggedUsersResponse> response = restTemplate.exchange(
                    ApplicationConstants.API_GET_LOGGED_USERS_ENDPOINT,
                    HttpMethod.GET,
                    new HttpEntity<>(null, sessionService.getJwtHeaders()),
                    TelbotLoggedUsersResponse.class,
                    Collections.emptyMap()
            );

            if (response.getStatusCode().equals(HttpStatus.OK) && response.getBody() != null) {
                users = response.getBody().getUsers();
            }
        } catch (HttpStatusCodeException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void loginUserInService(User user, Chat chat, String username) {
        TelbotLoginUserRequest request = new TelbotLoginUserRequest();
        request.setUsername(username);
        request.setTelUserId(user.getId());
        request.setTelChatId(chat.getId());

        try {
            ResponseEntity<TelbotLoginUserResponse> response = restTemplate.exchange(
                    ApplicationConstants.API_LOGIN_ENDPOINT,
                    HttpMethod.POST,
                    new HttpEntity<>(request, sessionService.getJwtHeaders()),
                    TelbotLoginUserResponse.class,
                    Collections.emptyMap()
            );

            if (response.getBody() != null) {
                WflowUser wflowUser = new WflowUser();
                wflowUser.setUserId(UUID.fromString(response.getBody().getUserId()));
            } else {
                throw new LoginException(BotMessageEnum.EXCEPTION_.getMessage());
            }
        } catch (HttpClientErrorException e) {
            throw new LoginException(e.getMessage());
        }

    }

    @Override
    public WflowUser findWflowUser(final User user) {
        return users
                .stream()
                .filter(u -> Objects.equals(u.getTelUserId(), user.getId()))
                .findFirst()
                .orElse(createNewWflowUser(user));
    }

    @Override
    public WflowUser createNewWflowUser(User user) {
        WflowUser wflowUser = new WflowUser();
        wflowUser.setTelUserId(user.getId());
        users.add(wflowUser);
        return wflowUser;
    }

    @Override
    public boolean checkVerificationCode(User user, String code) {
        TelbotVerifyCodeRequest request = new TelbotVerifyCodeRequest();
        request.setTelUserId(user.getId());
        request.setCode(code);

        try {
            ResponseEntity<TelbotVerifyCodeResponse> response = restTemplate.exchange(
                    ApplicationConstants.API_VERIFY_CODE_ENDPOINT,
                    HttpMethod.POST,
                    new HttpEntity<>(request, sessionService.getJwtHeaders()),
                    TelbotVerifyCodeResponse.class,
                    Collections.emptyMap()
            );

            if (response.getBody() != null) {
                loadLoggedUsers();
                return response.getBody().isVerified();
            }
        } catch (HttpClientErrorException e) {
            throw new LoginException(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean isUserLoggedIn(User user) {
        loadLoggedUsers();
        return this.users.stream().anyMatch(u -> Objects.equals(u.getTelUserId(), user.getId()));
    }

    @Override
    public boolean isUserLoggedIn(Long userId) {
        loadLoggedUsers();
        return this.users.stream().anyMatch(u -> Objects.equals(u.getTelUserId(), userId));
    }

    @Override
    public boolean isUserSubscribeOnTasks(User user) {
        WflowUser wflowUser = findWflowUser(user);
        return wflowUser != null && wflowUser.getUserSettings() != null ? wflowUser.getUserSettings().getSubscribeOnTasks() : false;
    }

    @Override
    public boolean isUserSubscribeOnCalendar(User user) {
        WflowUser wflowUser = findWflowUser(user);
        return wflowUser != null && wflowUser.getUserSettings() != null ? wflowUser.getUserSettings().getSubscribeOnCalendar() : false;
    }

    @Override
    public boolean updateUserSettings(User user, String callbackData) {
        Boolean updated = Boolean.FALSE;
        UserSettings userSettings = findWflowUser(user).getUserSettings();

        switch (callbackData) {
            case CALLBACK_QUERY_TASKS_SUBSCRIBE:
                userSettings.setSubscribeOnTasks(Boolean.TRUE);
                break;
            case CALLBACK_QUERY_TASKS_UNSUBSCRIBE:
                userSettings.setSubscribeOnTasks(Boolean.FALSE);
                break;
            case CALLBACK_QUERY_CALENDAR_SUBSCRIBE:
                userSettings.setSubscribeOnCalendar(Boolean.TRUE);
                break;
            case CALLBACK_QUERY_CALENDAR_UNSUBSCRIBE:
                userSettings.setSubscribeOnCalendar(Boolean.FALSE);
                break;
        }

        try {
            TelbotUpdateUserSettingsRequest request = TelbotUpdateUserSettingsRequest
                    .builder()
                    .telUserId(user.getId())
                    .userSettings(userSettings)
                    .build();

            ResponseEntity<Boolean> response = restTemplate.exchange(
                    ApplicationConstants.API_SETTINGS_ENDPOINT,
                    HttpMethod.PUT,
                    new HttpEntity<>(request, sessionService.getJwtHeaders()),
                    Boolean.class,
                    Collections.emptyMap()
            );

            if (response.getBody() != null) {
                updated = response.getBody();
            }

        } catch (HttpStatusCodeException e) {
            logger.error(e.getMessage());
        }

        return updated;
    }

    @Override
    public boolean logout(Long telUserId) {
        boolean loggedOut = false;

        try {

            ResponseEntity<Boolean> response = restTemplate.exchange(
                    ApplicationConstants.API_LOGOUT_ENDPOINT,
                    HttpMethod.POST,
                    new HttpEntity<>(telUserId, sessionService.getJwtHeaders()),
                    Boolean.class,
                    Collections.emptyMap()
            );

            if (response.getBody() != null) {
                loggedOut = response.getBody();
            }

        } catch (HttpStatusCodeException e) {
            logger.error(e.getMessage());
        }

        return loggedOut;
    }


}

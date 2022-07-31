package com.pentyugov.wflowappbot.application.service.impl;

import com.pentyugov.wflowappbot.application.ApplicationConstants;
import com.pentyugov.wflowappbot.application.bot.BotMessageEnum;
import com.pentyugov.wflowappbot.application.model.WflowUser;
import com.pentyugov.wflowappbot.application.rest.payload.LoginException;
import com.pentyugov.wflowappbot.application.rest.payload.request.TelegramLoginUserRequest;
import com.pentyugov.wflowappbot.application.rest.payload.request.TelegramVerifyCodeRequest;
import com.pentyugov.wflowappbot.application.rest.payload.response.TelegramLoggedUsersResponse;
import com.pentyugov.wflowappbot.application.rest.payload.response.TelegramLoginUserResponse;
import com.pentyugov.wflowappbot.application.rest.payload.response.TelegramVerifyCodeResponse;
import com.pentyugov.wflowappbot.application.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service(UserService.NAME)
public class UserServiceImpl implements UserService {

    private List<WflowUser> users = new ArrayList<>();

    @Override
    public void loadLoggedUsers() {
        RestTemplate restTemplate = new RestTemplate();
        TelegramLoggedUsersResponse response =
                restTemplate.getForObject(ApplicationConstants.API_GET_LOGGED_USERS_ENDPOINT, TelegramLoggedUsersResponse.class);

        if (response != null && response.getHttpStatus().equals(HttpStatus.OK)) {
            users = response.getUsers();
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
    public void loginUserInService(User user, Chat chat, String username) {
        TelegramLoginUserRequest request = new TelegramLoginUserRequest();
        request.setUsername(username);
        request.setTelUserId(user.getId());
        request.setTelChatId(chat.getId());

        RestTemplate restTemplate = new RestTemplate();
        try {
            TelegramLoginUserResponse response =
                    restTemplate.postForObject(ApplicationConstants.API_LOGIN_ENDPOINT, request, TelegramLoginUserResponse.class);
            if (response != null) {
                WflowUser wflowUser = new WflowUser();
                wflowUser.setUserId(UUID.fromString(response.getUserId()));
            } else {
                throw new LoginException(BotMessageEnum.EXCEPTION_LOGIN.getMessage());
            }
        } catch (HttpClientErrorException e) {
            throw new LoginException(e.getMessage());
        }

    }

    @Override
    public boolean checkVerificationCode(User user, String code) {
        TelegramVerifyCodeRequest request = new TelegramVerifyCodeRequest();
        request.setTelUserId(user.getId());
        request.setCode(code);

        RestTemplate restTemplate = new RestTemplate();
        TelegramVerifyCodeResponse response =
                restTemplate.postForObject(ApplicationConstants.API_VERIFY_CODE_ENDPOINT, request, TelegramVerifyCodeResponse.class);

        if (response != null) {
            loadLoggedUsers();
            return response.isVerified();
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


}

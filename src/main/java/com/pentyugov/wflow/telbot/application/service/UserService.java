package com.pentyugov.wflow.telbot.application.service;

import com.pentyugov.wflow.telbot.application.model.WflowUser;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;

public interface UserService {
    String NAME = "tel$UserService";

    void loadLoggedUsers();
    WflowUser findWflowUser(User user);
    WflowUser createNewWflowUser(User user);
    void loginUserInService(User user, Chat chat, String username);
    boolean checkVerificationCode(User user, String code);
    boolean isUserLoggedIn(User user);
    boolean isUserLoggedIn(Long userId);
    boolean isUserSubscribeOnTasks(User user);
    boolean isUserSubscribeOnCalendar(User user);
    boolean updateUserSettings(User user, String callbackData);
    boolean logout(Long telUserId);
}

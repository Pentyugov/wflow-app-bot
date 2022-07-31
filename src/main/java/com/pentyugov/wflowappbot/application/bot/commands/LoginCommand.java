package com.pentyugov.wflowappbot.application.bot.commands;

import com.pentyugov.wflowappbot.application.bot.BotMessageEnum;
import com.pentyugov.wflowappbot.application.bot.MessageMaker;
import com.pentyugov.wflowappbot.application.bot.Utils;
import com.pentyugov.wflowappbot.application.rest.payload.LoginException;
import com.pentyugov.wflowappbot.application.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class LoginCommand extends ServiceCommand {

    @Autowired
    private UserService userService;
    @Autowired
    private MessageMaker messageMaker;

    private final Logger logger = LoggerFactory.getLogger(LoginCommand.class);

    public LoginCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        BotMessageEnum answer;

        if (userService.isUserLoggedIn(user)) {
            answer = BotMessageEnum.ALREADY_LOGGED_IN;
        } else {
            if (strings.length > 0) {
                String username = strings[0];
                try {
                    userService.loginUserInService(user, chat, username);
                    answer = BotMessageEnum.LOGIN_CODE_SEND;

                } catch (LoginException e) {
                    logger.error(e.getMessage());
                    answer = BotMessageEnum.EXCEPTION_LOGIN;
                }

            } else {
                answer = BotMessageEnum.LOGIN_EMPTY;
            }
        }

        sendAnswer(absSender, messageMaker.createMessage(user, chat, answer.getMessage()));
    }
}
package com.pentyugov.wflowappbot.application.bot;

import com.pentyugov.wflowappbot.application.bot.keyboard.KeyboardMaker;
import com.pentyugov.wflowappbot.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;

@Component
public class MessageMaker {

    private final UserService userService;
    private final KeyboardMaker keyboardMaker;

    @Autowired
    public MessageMaker(UserService userService, KeyboardMaker keyboardMaker) {
        this.userService = userService;
        this.keyboardMaker = keyboardMaker;
    }

    public SendMessage createStartMessage(User user, Chat chat) {
        SendMessage sendMessage;

        if (!userService.isUserLoggedIn(user)) {
            sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(chat.getId()));
            sendMessage.setText(BotMessageEnum.START_MESSAGE_NOT_LOGGED_IN.getMessage());
            sendMessage.enableMarkdown(true);
            sendMessage.setReplyMarkup(keyboardMaker.getMainMenuNotLoggedInKeyboard());

        } else {
            sendMessage = createMessage(user, chat, BotMessageEnum.START_MESSAGE_LOGGED_IN.getMessage());
        }

        return sendMessage;
    }

    public SendMessage createMessage(User user, Chat chat, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chat.getId()));
        sendMessage.setText(text);

        if (userService.isUserLoggedIn(user)) {
            sendMessage.enableMarkdown(true);
            sendMessage.setReplyMarkup(keyboardMaker.getMainMenuKeyboard());
        }
        return sendMessage;
    }
}

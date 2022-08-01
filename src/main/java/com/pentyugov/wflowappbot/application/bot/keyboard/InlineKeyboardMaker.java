package com.pentyugov.wflowappbot.application.bot.keyboard;

import com.pentyugov.wflowappbot.application.service.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;
import static com.pentyugov.wflowappbot.application.bot.keyboard.InlineKeyboardConstants.*;

/**
 * Клавиатуры, формируемые в ленте Telegram для получения файлов
 */
@Component
public class InlineKeyboardMaker {

    private final UserService userService;



    public InlineKeyboardMaker(UserService userService) {
        this.userService = userService;
    }

    public InlineKeyboardMarkup getInlineSettingsKeyboard(User user) {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        rowList.add(getRowButtons(Collections.singletonMap(Name.INLINE_LOGOUT, CallbackQueryAction.CALLBACK_QUERY_LOGOUT)));
        rowList.add(getRowButtons(getTaskSubscribeButtonData(user)));
        rowList.add(getRowButtons(getCalendarSubscribeButtonData(user)));

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    private Map<String, String> getTaskSubscribeButtonData(User user) {
        Map<String, String> map = new HashMap<>();
        boolean isSubscribe = userService.isUserSubscribeOnTasks(user);
        if (isSubscribe) {
            map.put(Name.TASK_UNSUBSCRIBE, CallbackQueryAction.CALLBACK_QUERY_TASKS_UNSUBSCRIBE);
        } else {
            map.put(Name.TASK_SUBSCRIBE, CallbackQueryAction.CALLBACK_QUERY_TASKS_SUBSCRIBE);
        }
        return map;
    }

    private Map<String, String> getCalendarSubscribeButtonData(User user) {
        Map<String, String> map = new HashMap<>();
        boolean isSubscribe = userService.isUserSubscribeOnCalendar(user);
        if (isSubscribe) {
            map.put(Name.CALENDAR_UNSUBSCRIBE, CallbackQueryAction.CALLBACK_QUERY_CALENDAR_UNSUBSCRIBE);
        } else {
            map.put(Name.CALENDAR_SUBSCRIBE, CallbackQueryAction.CALLBACK_QUERY_CALENDAR_SUBSCRIBE);
        }
        return map;
    }

    private List<InlineKeyboardButton> getRowButtons(Map<String, String> buttonNamesMap) {
        final List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();

        for (Map.Entry<String, String> entry : buttonNamesMap.entrySet()) {
            keyboardButtonsRow.add(getButton(entry.getKey(), entry.getValue()));
        }

        return keyboardButtonsRow;
    }

    private InlineKeyboardButton getButton(String buttonName, String buttonCallBackData, String inlineQueryCurrentChat) {
        InlineKeyboardButton button = getButton(buttonName, buttonCallBackData);
        button.setSwitchInlineQueryCurrentChat(inlineQueryCurrentChat);
        return button;
    }

    private InlineKeyboardButton getButton(String buttonName, String buttonCallBackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(buttonName);
        button.setCallbackData(buttonCallBackData);
                return button;
    }

    private List<InlineKeyboardButton> getNextPrevButtonsRow() {
        Map<String, String> map = new HashMap<>();
        map.put("<<", "/prev");
        map.put(">>", "/next");

        return getRowButtons(map);
    }
}


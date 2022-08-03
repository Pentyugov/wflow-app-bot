package com.pentyugov.wflowappbot.application.bot.keyboard;

import com.pentyugov.wflowappbot.application.ApplicationConstants;
import com.pentyugov.wflowappbot.application.bot.BotMessageEnum;
import com.pentyugov.wflowappbot.application.model.WflowTask;
import com.pentyugov.wflowappbot.application.service.TaskService;
import com.pentyugov.wflowappbot.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;
import static com.pentyugov.wflowappbot.application.bot.keyboard.InlineKeyboardConstants.Name.*;
import static com.pentyugov.wflowappbot.application.bot.keyboard.InlineKeyboardConstants.CallbackQueryAction.*;

@Component
@RequiredArgsConstructor
public class InlineKeyboardMaker {

    private final UserService userService;
    private final TaskService taskService;

    public InlineKeyboardMarkup getInlineSettingsKeyboard(User user) {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        rowList.add(getRowButtons(Collections.singletonMap(INLINE_LOGOUT, CALLBACK_QUERY_LOGOUT)));
        rowList.add(getRowButtons(getTaskSubscribeButtonData(user)));
        rowList.add(getRowButtons(getCalendarSubscribeButtonData(user)));

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getInlineMyTasksKeyboard(User user, List<WflowTask> tasks) {
        final List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        tasks.forEach(task -> {
            rowList.add(getRowButtons((Collections.singletonMap(String.format(BotMessageEnum.TEMPLATE_TASK.getMessage(), task.getNumber()), "/task_" + task.getId()))));
        });

        Map<String, String> map = new HashMap<>();
        if (taskService.getCurrentPageNumber(user) > 0)
            map.put("<<", CALLBACK_QUERY_TASKS_PREV);
        if (ApplicationConstants.TASKS_PER_PAGE <= tasks.size())
            map.put(">>", CALLBACK_QUERY_TASKS_NEXT);


        rowList.add(getRowButtons(map));

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    private Map<String, String> getTaskSubscribeButtonData(User user) {
        Map<String, String> map = new HashMap<>();
        boolean isSubscribe = userService.isUserSubscribeOnTasks(user);
        if (isSubscribe) {
            map.put(TASK_UNSUBSCRIBE, CALLBACK_QUERY_TASKS_UNSUBSCRIBE);
        } else {
            map.put(TASK_SUBSCRIBE, CALLBACK_QUERY_TASKS_SUBSCRIBE);
        }
        return map;
    }

    private Map<String, String> getCalendarSubscribeButtonData(User user) {
        Map<String, String> map = new HashMap<>();
        boolean isSubscribe = userService.isUserSubscribeOnCalendar(user);
        if (isSubscribe) {
            map.put(CALENDAR_UNSUBSCRIBE, CALLBACK_QUERY_CALENDAR_UNSUBSCRIBE);
        } else {
            map.put(CALENDAR_SUBSCRIBE, CALLBACK_QUERY_CALENDAR_SUBSCRIBE);
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

    private List<InlineKeyboardButton> getNextPrevButtonsRow(String callbackPrev, String callbackNext) {
        Map<String, String> map = new HashMap<>();
        map.put("<<", callbackPrev);
        map.put(">>", callbackNext);

        return getRowButtons(map);
    }
}


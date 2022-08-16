package com.pentyugov.wflow.telbot.application.bot.handler;

import com.pentyugov.wflow.telbot.application.aspect.Connected;
import com.pentyugov.wflow.telbot.application.bot.enums.BotMessageEnum;
import com.pentyugov.wflow.telbot.application.bot.keyboard.InlineKeyboardMaker;
import com.pentyugov.wflow.telbot.application.bot.keyboard.KeyboardMaker;
import com.pentyugov.wflow.telbot.application.model.WflowTask;
import com.pentyugov.wflow.telbot.application.service.TaskService;
import com.pentyugov.wflow.telbot.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;

import java.text.SimpleDateFormat;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MessageMaker {

    private final UserService userService;
    private final KeyboardMaker keyboardMaker;
    private final InlineKeyboardMaker inlineKeyboardMaker;
    private final TaskService taskService;

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

    @Connected
    public SendMessage creatTaskAssignedMessage(Long chaId, WflowTask task) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chaId));
        sendMessage.setText(generateFromTemplate(BotMessageEnum.TEMPLATE_TASK_ASSIGNED, task));
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(keyboardMaker.getMainMenuKeyboard());
        return sendMessage;
    }

    @Connected
    public SendMessage creatTaskOverdueMessage(Long chaId, WflowTask task) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chaId));
        sendMessage.setText(generateFromTemplate(BotMessageEnum.TEMPLATE_TASK_OVERDUE, task));
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(keyboardMaker.getMainMenuKeyboard());
        return sendMessage;
    }

    @Connected
    public SendMessage createTaskDataMessage(User user, Chat chat, String taskId) {
        WflowTask task = taskService.getTaskById(user, taskId);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chat.getId()));
        sendMessage.setText(generateFromTemplate(BotMessageEnum.TEMPLATE_TASK_DATA, task));
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(keyboardMaker.getMainMenuKeyboard());
        return sendMessage;
    }

    @Connected
    public SendMessage createTaskUpdatedMessage(User user, Chat chat, String callbackData) {
        boolean updated = userService.updateUserSettings(user, callbackData);
        String message = updated ? BotMessageEnum.SETTINGS_UPDATED.getMessage() : BotMessageEnum.EXCEPTION_.getMessage();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chat.getId()));
        sendMessage.setText(message);
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(keyboardMaker.getMainMenuKeyboard());
        return sendMessage;

    }

    @Connected
    public SendMessage createLogoutMessage(User user, Chat chat) {
        boolean loggedOut = userService.logout(user.getId());

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chat.getId()));
        sendMessage.setText(loggedOut ? BotMessageEnum.LOGOUT_MESSAGE.getMessage() : BotMessageEnum.EXCEPTION_.getMessage());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(keyboardMaker.getMainMenuKeyboard());
        return sendMessage;
    }

    @Connected
    private String generateFromTemplate(BotMessageEnum template, WflowTask task) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return template.getMessage()
                .replace("$number", task.getNumber())
                .replace("$priority", getPriority(task.getPriority()))
                .replace("$project", task.getProject() != null ? task.getProject() : " - ")
                .replace("$dueDate", dateFormat.format(task.getDueDate()))
                .replace("$description", task.getDescription())
                .replace("$comment", task.getComment());
    }

    public SendMessage createMessage(User user, Chat chat, BotMessageEnum messageEnum) {
        return createMessage(user, chat, messageEnum.getMessage());
    }

    @Connected
    public SendMessage createSettingsMessage(User user, Chat chat) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chat.getId()));
        sendMessage.setText(BotMessageEnum.SETTINGS_MESSAGE.getMessage());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(inlineKeyboardMaker.getInlineSettingsKeyboard(user));
        return sendMessage;
    }

    @Connected
    public SendMessage createPrevMyTasksMessage(User user, Chat chat) {
        List<WflowTask> tasks = taskService.getPrevTaskPage(user);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chat.getId()));
        sendMessage.setText(BotMessageEnum.MY_TASKS_MESSAGE.getMessage());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(inlineKeyboardMaker.getInlineMyTasksKeyboard(user, tasks));
        return sendMessage;
    }

    @Connected
    public SendMessage createNextMyTasksMessage(User user, Chat chat) {
        List<WflowTask> tasks = taskService.getNextTaskPage(user);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chat.getId()));
        sendMessage.setText(BotMessageEnum.MY_TASKS_MESSAGE.getMessage());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(inlineKeyboardMaker.getInlineMyTasksKeyboard(user, tasks));
        return sendMessage;
    }

    @Connected
    public SendMessage createMyTasksMessage(User user, Chat chat) {
        List<WflowTask> tasks = taskService.getTaskPage(user, 0);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chat.getId()));
        sendMessage.setText(BotMessageEnum.MY_TASKS_MESSAGE.getMessage());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(inlineKeyboardMaker.getInlineMyTasksKeyboard(user, tasks));
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

    public SendMessage createNotConnectedToServerMessage(Chat chat) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chat.getId()));
        sendMessage.setText(BotMessageEnum.APPLICATION_NOT_CONNECTED.getMessage());
        return sendMessage;
    }

    private String getPriority(String priority) {
        if (priority.equals(WflowTask.PRIORITY_LOW))
            return BotMessageEnum.TASK_PRIORITY_LOW.getMessage();
        else if (priority.equals(WflowTask.PRIORITY_MEDIUM))
            return BotMessageEnum.TASK_PRIORITY_MEDIUM.getMessage();
        else if (priority.equals(WflowTask.PRIORITY_HIGH))
            return BotMessageEnum.TASK_PRIORITY_HIGH.getMessage();

        return "";
    }

}

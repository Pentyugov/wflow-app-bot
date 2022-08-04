package com.pentyugov.wflowappbot.application.bot;

import com.pentyugov.wflowappbot.application.bot.aspect.Connected;
import com.pentyugov.wflowappbot.application.bot.keyboard.InlineKeyboardMaker;
import com.pentyugov.wflowappbot.application.bot.keyboard.KeyboardMaker;
import com.pentyugov.wflowappbot.application.model.WflowTask;
import com.pentyugov.wflowappbot.application.service.TaskService;
import com.pentyugov.wflowappbot.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    public SendMessage creatTaskAssignedMessage(Long chaId, WflowTask task) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String message = BotMessageEnum.TEMPLATE_TASK_ASSIGNED.getMessage()
                .replace("$number", task.getNumber())
                .replace("$priority", getPriority(task.getPriority()))
                .replace("$project", task.getProject() != null ? task.getProject() : " - ")
                .replace("$dueDate", dateFormat.format(task.getDueDate()))
                .replace("$description", task.getDescription())
                .replace("$comment", task.getComment());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chaId));
        sendMessage.setText(message);
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(keyboardMaker.getMainMenuKeyboard());
        return sendMessage;
    }

    public SendMessage creatTaskOverdueMessage(Long chaId, WflowTask task) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String message = BotMessageEnum.TEMPLATE_TASK_OVERDUE.getMessage()
                .replace("$number", task.getNumber())
                .replace("$priority", getPriority(task.getPriority()))
                .replace("$project", task.getProject() != null ? task.getProject() : " - ")
                .replace("$dueDate", dateFormat.format(task.getDueDate()))
                .replace("$description", task.getDescription())
                .replace("$comment", task.getComment());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chaId));
        sendMessage.setText(message);
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(keyboardMaker.getMainMenuKeyboard());
        return sendMessage;
    }

    public SendMessage createMessage(User user, Chat chat, BotMessageEnum messageEnum) {
        return createMessage(user, chat, messageEnum.getMessage());
    }

    public SendMessage createSettingsMessage(User user, Chat chat) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chat.getId()));
        sendMessage.setText(BotMessageEnum.SETTINGS_MESSAGE.getMessage());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(inlineKeyboardMaker.getInlineSettingsKeyboard(user));
        return sendMessage;
    }

    public SendMessage createPrevMyTasksMessage(User user, Chat chat) {
        List<WflowTask> tasks = taskService.getPrevTaskPage(user);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chat.getId()));
        sendMessage.setText(BotMessageEnum.MY_TASKS_MESSAGE.getMessage());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(inlineKeyboardMaker.getInlineMyTasksKeyboard(user, tasks));
        return sendMessage;
    }

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

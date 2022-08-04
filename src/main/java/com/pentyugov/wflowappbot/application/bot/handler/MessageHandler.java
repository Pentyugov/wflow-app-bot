package com.pentyugov.wflowappbot.application.bot.handler;

import com.pentyugov.wflowappbot.application.bot.BotMessageEnum;
import com.pentyugov.wflowappbot.application.bot.MessageMaker;
import com.pentyugov.wflowappbot.application.bot.keyboard.ButtonNameEnum;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.pentyugov.wflowappbot.application.bot.keyboard.InlineKeyboardConstants.CallbackQueryAction.CALLBACK_QUERY_TASKS_NEXT;
import static com.pentyugov.wflowappbot.application.bot.keyboard.InlineKeyboardConstants.CallbackQueryAction.CALLBACK_QUERY_TASKS_PREV;

@Component
public class MessageHandler {

    private final MessageMaker messageMaker;

    public MessageHandler(MessageMaker messageMaker) {

        this.messageMaker = messageMaker;
    }

    public SendMessage getAnswerMessage(Message message) {
        SendMessage sendMessage;
        String inputMessage = message.getText();

        if (ButtonNameEnum.HELP_BUTTON.getButtonName().equals(inputMessage)) {
            sendMessage = messageMaker.createMessage(message.getFrom(), message.getChat(), BotMessageEnum.HELP_MESSAGE.getMessage());

        } else if (ButtonNameEnum.MY_TASKS_BUTTON.getButtonName().equals(inputMessage)) {
            sendMessage = messageMaker.createMyTasksMessage(message.getFrom(), message.getChat());

        } else if (ButtonNameEnum.SETTINGS_BUTTON.getButtonName().equals(inputMessage)) {
            sendMessage = messageMaker.createSettingsMessage(message.getFrom(), message.getChat());

        } else {
            sendMessage = messageMaker.createMessage(message.getFrom(), message.getChat(), BotMessageEnum.DEFAULT_WRONG_REQUEST_MESSAGE.getMessage());
        }

        return sendMessage;
    }

    public SendMessage getNotConnectedToServerMessage(Chat chat) {
        return messageMaker.createNotConnectedToServerMessage(chat);
    }

    public SendMessage handleCallbackQuery(CallbackQuery callbackQuery) {
        if (CALLBACK_QUERY_TASKS_PREV.equals(callbackQuery.getData())) {
            return messageMaker.createPrevMyTasksMessage(callbackQuery.getFrom(), callbackQuery.getMessage().getChat());
        }
        if (CALLBACK_QUERY_TASKS_NEXT.equals(callbackQuery.getData())) {
            return messageMaker.createNextMyTasksMessage(callbackQuery.getFrom(), callbackQuery.getMessage().getChat());
        }
        return null;
    }
}

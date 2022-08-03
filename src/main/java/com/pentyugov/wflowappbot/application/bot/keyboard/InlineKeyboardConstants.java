package com.pentyugov.wflowappbot.application.bot.keyboard;

public class InlineKeyboardConstants {

    public interface Name {
        String INLINE_LOGOUT = "Разлогиниться";
        String TASK_SUBSCRIBE = "Подписаться на уведомления по задачам";
        String CALENDAR_SUBSCRIBE = "Подписаться на уведомления календаря";
        String TASK_UNSUBSCRIBE = "Отписаться от уведомлений по задачам";
        String CALENDAR_UNSUBSCRIBE = "Отписаться от уведомлений календаря";
    }

    public interface CallbackQueryAction {
        String CALLBACK_QUERY_LOGOUT = "/logout";
        String CALLBACK_QUERY_TASKS_UNSUBSCRIBE = "/tasks_unsubscribe";
        String CALLBACK_QUERY_CALENDAR_UNSUBSCRIBE = "/calendar_unsubscribe";
        String CALLBACK_QUERY_TASKS_SUBSCRIBE = "/tasks_subscribe";
        String CALLBACK_QUERY_CALENDAR_SUBSCRIBE = "/calendar_subscribe";
        String CALLBACK_QUERY_TASKS_PREV = "/tasks_prev";
        String CALLBACK_QUERY_TASKS_NEXT = "/tasks_next";
    }


}

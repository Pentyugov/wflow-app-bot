package com.pentyugov.wflow.telbot.application.bot.keyboard;

public enum ButtonNameEnum {
    MY_TASKS_BUTTON("Мои задачи"),
    SETTINGS_BUTTON("Настройки"),
    HELP_BUTTON("Помощь"),

    INLINE_LOGOUT("Разлогиниться");

    private final String buttonName;

    ButtonNameEnum(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonName() {
        return buttonName;
    }
}

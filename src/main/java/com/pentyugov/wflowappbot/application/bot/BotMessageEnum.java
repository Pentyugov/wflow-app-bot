package com.pentyugov.wflowappbot.application.bot;

public enum BotMessageEnum {

    APPLICATION_NOT_CONNECTED("\u26D4 Нет соединения с сервером, попробуйте позже"),

    START_MESSAGE_NOT_LOGGED_IN("\uD83D\uDC4B Привет, я бот помощник для приложения Wflow-app\n\n" +
            "\u27A1 Для авторизации под пользователем системы Wflow-app наберите команду:\n /login {логин пользователя системы Wflow-app}\n\n\n" +
            "Воспользуйтесь клавиатурой, чтобы начать работу\uD83D\uDC47"
    ),

    START_MESSAGE_LOGGED_IN("\uD83D\uDC4B Привет, я бот помощник для приложения Wflow-app\n\n" +
            "\u2705 Вы уже авторизованы!\n\n" +
            "Воспользуйтесь клавиатурой, чтобы начать работу\uD83D\uDC47"
    ),

    ALREADY_LOGGED_IN("\u2705 Вы уже авторизованы!\n\n" +
            "Воспользуйтесь клавиатурой, чтобы начать работу\uD83D\uDC47"),

    CODE_LOGIN_SUCCESS("\u2705 Авторизация прошла успешно!\n" +
            "Воспользуйтесь клавиатурой, чтобы начать работу\uD83D\uDC47"
            ),

    CODE_WRONG("\u26D4 Неверный код верификации!\n" +
            "Для верификации наберите /code {логин пользователя системы Wflow}"),

    CODE_EMPTY("\u26D4 Не указан код верификации пользователя!\n" +
            "Для верификации наберите /code {логин пользователя системы Wflow}"),

    LOGIN_CODE_SEND("\u2705 Отлично!\n" +
            "\u2709 На электронную почту направлен код верификации\n" +
            "\uD83D\uDC49 Для продолжения авторизации наберите /code {код верификации из электронного письма}"),

    LOGIN_EMPTY("\u26D4 Не указано имя пользователя!\n" +
            "\u27A1 Для авторизации под пользователем системы Wflow-app наберите команду:\n /login {логин пользователя системы Wflow-app}"),

    EXCEPTION_("\u26D4 Что то пошло не так\n" +
            "Обратитесь к администратору системы"),

    TEMPLATE_TASK("\uD83D\uDCDD Задача №%s"),

    TEMPLATE_TASK_DATA("\u2705 Задача № $number!\n\n" +
            "\uD83D\uDCCA Приоритет: $priority \n\n" +
            "\uD83D\uDCC1 Проект: $project \n\n" +
            "\uD83D\uDCC5 Срок выполнения: $dueDate \n\n" +
            "\uD83D\uDCDD Описание: $description \n\n" +
            "\u270D Комментарий: $comment"),

    TEMPLATE_TASK_ASSIGNED("\u2705 Вам назначена задача № $number!\n\n" +
            "\uD83D\uDCCA Приоритет: $priority \n\n" +
            "\uD83D\uDCC1 Проект: $project \n\n" +
            "\uD83D\uDCC5 Срок выполнения: $dueDate \n\n" +
            "\uD83D\uDCDD Описание: $description \n\n" +
            "\u270D Комментарий: $comment"),

    TEMPLATE_TASK_OVERDUE("❌ Задача № $number просрочена!\n\n" +
            "\uD83D\uDCCA Приоритет: $priority \n\n" +
            "\uD83D\uDCC1 Проект: $project \n\n" +
            "\uD83D\uDCC5 Срок выполнения: $dueDate \n\n" +
            "\uD83D\uDCDD Описание: $description \n\n" +
            "\u270D Комментарий: $comment"),

    HELP_MESSAGE("\uD83D\uDC4B Привет, я бот помощник системы Wflow-app\n\n" +
            "❗ *Что я умею делать:*\n\n" +
            "✅ Получать уведомления о назначенных Вам задачах\n\n" +
            "✅ Получать уведомления о событиях календаря\n\n" +
            "✅ Выполнять действия назначенных Вам задач\n\n" +
            "✅ Уведомлять о просроченных задачах\n\n\n" +
            "\u27A1 Для авторизации под пользователем системы Wflow-app наберите команду:\n /login {логин пользователя системы Wflow-app}\n\n\n" +
            "Воспользуйтесь клавиатурой, чтобы начать работу\uD83D\uDC47"),

    SETTINGS_MESSAGE("\uD83D\uDD27 Настройки"),

    SETTINGS_UPDATED("\uD83D\uDD27 Настройки успешно обновлены"),

    LOGOUT_MESSAGE("\uD83D\uDD27 Вы успешно вышли из системы"),

    MY_TASKS_MESSAGE("✅  Мои задачи"),

    TASK_PRIORITY_LOW("Низкий"),

    TASK_PRIORITY_MEDIUM("Средний"),

    TASK_PRIORITY_HIGH("Высокий"),

    DEFAULT_WRONG_REQUEST_MESSAGE("\u26D4 Не корректный запрос!\n" +
            "Для получения справки наберите /help"),

    CHOOSE_DICTIONARY_MESSAGE("Выберите словарь\uD83D\uDC47 "),
    UPLOAD_DICTIONARY_MESSAGE("Добавьте файл, соответствующий шаблону. Вы можете сделать это в любой момент"),
    NON_COMMAND_MESSAGE("Пожалуйста, воспользуйтесь клавиатурой\uD83D\uDC47"),

    //результаты загрузки словаря
    SUCCESS_UPLOAD_MESSAGE("\uD83D\uDC4D Словарь успешно загружен"),
    EXCEPTION_TELEGRAM_API_MESSAGE("Ошибка при попытку получить файл из API Telegram"),
    EXCEPTION_TOO_LARGE_DICTIONARY_MESSAGE("В словаре больше 1 000 слов. Едва ли такой большой набор словарных " +
            "слов действительно нужен, ведь я работаю для обучения детей"),
    EXCEPTION_BAD_FILE_MESSAGE("Файл не может быть обработан. Вы шлёте мне что-то не то, балуетесь, наверное"),

    //ошибки при обработке callback-ов
    EXCEPTION_BAD_BUTTON_NAME_MESSAGE("Неверное значение кнопки. Крайне странно. Попробуйте позже"),
    EXCEPTION_DICTIONARY_NOT_FOUND_MESSAGE("Словарь не найден"),
    EXCEPTION_DICTIONARY_WTF_MESSAGE("Нежиданная ошибка при попытке получить словарь. Сам в шоке"),
    EXCEPTION_TASKS_WTF_MESSAGE("Нежиданная ошибка при попытке получить задания. Сам в шоке"),
    EXCEPTION_TEMPLATE_WTF_MESSAGE("Нежиданная ошибка при попытке получить шаблон. Сам в шоке"),

    //прочие ошибки
    EXCEPTION_ILLEGAL_MESSAGE("Нет, к такому меня не готовили! Я работаю или с текстом, или с файлом"),
    EXCEPTION_WHAT_THE_FUCK("Что-то пошло не так. Обратитесь к программисту");

    private final String message;

    BotMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

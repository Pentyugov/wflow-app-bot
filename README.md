### Wflow-bot app

##### Стэк: Spring boot, Feign Client, RestTemplate

_____

### Introduction
Данное приложение является Telegram ботом-помощником приложения Wflow-app.

Демо приложение доступно по адресу:
`wflow-app.ru`

- Имя пользователя: testuser
- Пароль: testuser

Демо бот доступен по ссылке 

    t.me/wflow_app_bot


Основная серверная часть:

      https://github.com/Pentyugov/wflow

Клиентская часть:

      https://github.com/Pentyugov/zolloz-client-mat

Eureka server для регистрации Eureka клиентов:

      https://github.com/Pentyugov/wflow-eureka-server
____

### Запуск
Требования:
- JAVA 11
- Maven

Перед запуском приложения необходимо прописать следующие переменные среды
- `WFLOW_TEL_BOT_PORT` - Порт на котором будет запускаться приложение
- `WFLOW_TEL_BOT_NAME` - Имя телеграм бота получанное при регистрации
- `WFLOW_TEL_BOT_TOKEN` - Токен телеграм бота получанный при регистрации
- `WFLOW_TEL_BOT_USERNAME` - Имя пользователя системы Wflow-app
- `WFLOW_TEL_BOT_PASSWORD` - Пароль пользователя системы Wflow-app

После установки переменных среды выполнить команды:

    mvn clean package

    java - jar ./target/wflow-bot.jar



package com.pentyugov.wflowappbot.application.bot.commands;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandsConfiguration {

    @Bean
    public StartCommand startCommand() {
        return new StartCommand("start", "Старт");
    }

    @Bean
    public LoginCommand loginCommand() {
        return new LoginCommand("login", "Авторизация");
    }

    @Bean
    public LogoutCommand logoutCommand() {
        return new LogoutCommand("logout", "Выход из системы");
    }

    @Bean
    public CodeCommand codeCommand() {
        return new CodeCommand("code", "Код верификации");
    }

    @Bean
    public HelpCommand helpCommand() {
        return new HelpCommand("help", "Помощь");
    }

}

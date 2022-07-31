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
    public CodeCommand codeCommand() {
        return new CodeCommand("code", "Код верификации");
    }

}

package com.pentyugov.wflow.telbot.application.bot.configuration;

import com.pentyugov.wflow.telbot.application.bot.TelBot;
import com.pentyugov.wflow.telbot.application.bot.commands.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TelbotConfiguration {

    @Bean
    public StartCommand startCommand() {
        return new StartCommand("/start", "start command");
    }

    @Bean
    public CodeCommand codeCommand() {
        return new CodeCommand("/code", "code command");
    }

    @Bean
    public LoginCommand loginCommand() {
        return new LoginCommand("/login", "login command");
    }

    @Bean
    public LogoutCommand logoutCommand() {
        return new LogoutCommand("/logout", "logout command");
    }
    @Bean
    public HelpCommand helpCommand() {
        return new HelpCommand("/help", "help command");
    }

    @Bean(TelBot.NAME)
    public TelBot telBot() {
        return new TelBot(startCommand(), codeCommand(), loginCommand(), logoutCommand(), helpCommand());
    }
}

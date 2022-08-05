package com.pentyugov.wflowappbot.application.bot.aspect;

import com.pentyugov.wflowappbot.application.bot.Bot;
import com.pentyugov.wflowappbot.application.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;

@Aspect
@Component
@RequiredArgsConstructor
public class ConnectedAspect {

    private final SessionService sessionService;
    private final Bot bot;

    @Pointcut(value = "@annotation(connected) && args(user, chat)", argNames = "connected,user,chat")
    public void authenticate(Connected connected, User user, Chat chat) {

    }

    @Around(value = "authenticate(connected, user, chat)", argNames = "joinPoint,connected,user,chat")
    public Object around(ProceedingJoinPoint joinPoint,
                         Connected connected,
                         User user,
                         Chat chat) throws Throwable {
        if (sessionService.checkConnection()) {
            return joinPoint.proceed();
        }

        bot.sendConnectionRefusedMessage(chat);
        return null;
    }


}

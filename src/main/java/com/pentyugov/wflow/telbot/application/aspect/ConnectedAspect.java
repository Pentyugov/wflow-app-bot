package com.pentyugov.wflow.telbot.application.aspect;

import com.pentyugov.wflow.telbot.application.bot.TelBot;
import com.pentyugov.wflow.telbot.application.service.SessionService;
import com.pentyugov.wflow.telbot.application.service.UserService;
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
    private final UserService userService;
    private final TelBot telBot;

    @Pointcut(value = "@annotation(connected) && args(user, chat)", argNames = "connected,user,chat")
    public void authenticate(Connected connected, User user, Chat chat) {

    }

    @Around(value = "authenticate(connected, user, chat)", argNames = "joinPoint,connected,user,chat")
    public Object around(ProceedingJoinPoint joinPoint,
                         Connected connected,
                         User user,
                         Chat chat) throws Throwable {
        if (sessionService.checkConnection()) {
            if (userService.isUserLoggedIn(user)) {
                return joinPoint.proceed();
            }
            telBot.sendStartMessage(user, chat);
        } else {
            telBot.sendConnectionRefusedMessage(chat);
        }
        return null;
    }


}

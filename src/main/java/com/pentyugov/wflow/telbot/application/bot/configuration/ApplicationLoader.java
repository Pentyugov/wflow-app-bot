package com.pentyugov.wflow.telbot.application.bot.configuration;

import com.pentyugov.wflow.telbot.application.system.AppContext;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;

@Configuration
@RequiredArgsConstructor
public class ApplicationLoader implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationLoader.class);

    private final ApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        setApplicationContext();
    }

    private void setApplicationContext() {
        LOGGER.info("Initializing application context...");
        AppContext.setApplicationContext(applicationContext);
    }
}

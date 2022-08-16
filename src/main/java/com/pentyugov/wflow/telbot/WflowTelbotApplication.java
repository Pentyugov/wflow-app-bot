package com.pentyugov.wflow.telbot;

import com.pentyugov.wflow.telbot.application.bot.TelBot;
import com.pentyugov.wflow.telbot.application.service.SessionService;
import com.pentyugov.wflow.telbot.application.system.AppContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableAspectJAutoProxy
public class WflowTelbotApplication {

	public static void main(String[] args) {
		SpringApplication.run(WflowTelbotApplication.class, args);
		registerTelBot();
	}

	private static void registerTelBot() {
		long CHAT_ID = 438939028L;
		TelBot telBot = AppContext.get(TelBot.class);
		SessionService sessionService = AppContext.get(SessionService.class);
		try {
			TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
			botsApi.registerBot(telBot);

			SendMessage sendMessage = new SendMessage();
			sendMessage.setChatId(Long.toString(CHAT_ID));
			sendMessage.setText("Wflow-app bot started...");
			telBot.sendMessage(sendMessage);

			sessionService.authenticate();
		} catch (TelegramApiException e) {
			throw new RuntimeException(e);
		}
	}

}

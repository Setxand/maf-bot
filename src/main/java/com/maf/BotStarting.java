package com.maf;

import com.maf.telegram.client.TelegramClient;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class BotStarting {

	private final TelegramClient telegramClient;

	public BotStarting(TelegramClient telegramClient) {
		this.telegramClient = telegramClient;
	}

	@PostConstruct
	public void init() {
		telegramClient.setWebHook();
	}

}

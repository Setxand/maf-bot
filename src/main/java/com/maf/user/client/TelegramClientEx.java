package com.maf.user.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import telegram.Message;
import telegram.button.InlineKeyboardButton;
import telegram.client.TelegramClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class TelegramClientEx extends TelegramClient {
	public TelegramClientEx(@Value("${bot.url}") String telegramUrl, @Value("${server.url}") String serverUrl,
							@Value("${telegram.webhooks}") String webhook) {
		super(telegramUrl, serverUrl, webhook);
	}

	@Override
	public void sendActions(Message message) {
	}
}

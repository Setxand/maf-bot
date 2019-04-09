package com.maf.user.service;

import com.maf.user.client.TelegramClientEx;
import org.springframework.stereotype.Service;
import telegram.CallBackQuery;

@Service
public class CallBackDealerService {

	private final TelegramClientEx telegramClient;

	public CallBackDealerService(TelegramClientEx telegramClient) {
		this.telegramClient = telegramClient;
	}

	public void menu(CallBackQuery query) {


	}

	public void contacts(CallBackQuery query) {

	}

	public void makeOrder(CallBackQuery query) {

	}

	public void makeOrderDelivery(CallBackQuery query) {

	}
}

package com.maf.user.service;

import com.maf.telegram.CallBackQuery;
import com.maf.telegram.client.TelegramClient;
import org.springframework.stereotype.Service;

@Service
public class CallBackDealerService {

	private final TelegramClient telegramClient;

	public CallBackDealerService(TelegramClient telegramClient) {
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

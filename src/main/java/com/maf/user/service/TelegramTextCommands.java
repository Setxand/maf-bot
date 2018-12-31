package com.maf.user.service;

import com.maf.user.exception.BotException;

public enum TelegramTextCommands {
	START("/start"),
	MENU("Menu"),
	MAKE_ORDER("Make order"),
	MAKE_ORDER_DELIVERY("Make order with delivery"),
	CONTACTS("Contacts"),
	BROADCAST("Broadcast");

	private final String value;

	TelegramTextCommands(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static TelegramTextCommands getByVal(String val, Integer chatId) {

		for (TelegramTextCommands data : TelegramTextCommands.values()) {
			if (data.value.equals(val)) {
				return data;
			}
		}
		throw new BotException(chatId, "Sorry i don't know this command((");
	}
}

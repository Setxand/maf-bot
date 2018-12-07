package com.maf.user.service;

import com.maf.user.exception.BotException;

public enum CallBackData {
	START("/start"),
	MENU("Menu"),
	MAKE_ORDER("Make order"),
	MAKE_ORDER_DELIVERY("Make order with delivery"),
	CONTACTS("Contacts");

	private final String value;

	CallBackData(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static CallBackData getByVal(String val, Integer chatId) {

		for (CallBackData d : CallBackData.values()) {
			if (d.value.equals(val)) {
				return d;
			}
		}
		throw new BotException(chatId, "Sorry i don't know this command((");
	}
}

package com.maf.user.exception;

import lombok.Getter;

@Getter
public class BotException extends RuntimeException {
	private Integer chatId;

	public BotException(Integer chatId, String message) {
		super(message);
		this.chatId = chatId;
	}


}

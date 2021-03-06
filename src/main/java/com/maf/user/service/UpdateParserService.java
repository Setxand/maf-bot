package com.maf.user.service;

import org.springframework.stereotype.Service;
import telegram.Update;

@Service
public class UpdateParserService {

	private final MessageService messageService;
	private final CallBackService callBackService;
	public UpdateParserService(MessageService messageService, CallBackService callBackService) {
		this.messageService = messageService;
		this.callBackService = callBackService;
	}

	public void parseUpdate(Update update) {
		if (update.getMessage() != null) {
			messageService.parseMessage(update.getMessage());
		}
		else if (update.getCallBackQuery() != null) {
			callBackService.parseCallBackQuery(update.getCallBackQuery());
		}
	}
}

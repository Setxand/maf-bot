package com.maf.user.controller;


import com.maf.user.client.TelegramClientEx;
import com.maf.user.exception.BotException;
import com.maf.user.service.UpdateParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import telegram.Chat;
import telegram.Message;
import telegram.Update;

@RestController
@RequestMapping("/maf")
public class BotController {

	@Autowired UpdateParserService updateParserService;
	@Autowired TelegramClientEx telegramClient;

	@PostMapping
	public void getUpdate(@RequestBody Update update) {
		updateParserService.parseUpdate(update);
	}

	@ExceptionHandler(BotException.class)
	public void internalError(final BotException e) {
		telegramClient.simpleMessage(e.getMessage(), new Message(new Chat(e.getChatId())));
	}
}

package com.maf.user.controller;


import com.maf.telegram.TelegramRequest;
import com.maf.telegram.Update;
import com.maf.telegram.client.TelegramClient;
import com.maf.user.exception.BotException;
import com.maf.user.service.UpdateParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/maf")
public class BotController {

	@Autowired UpdateParserService updateParserService;
	@Autowired TelegramClient telegramClient;

	@PostMapping
	public void getUpdate(@RequestBody Update update) {
		updateParserService.parseUpdate(update);
	}

	@ExceptionHandler(BotException.class)
	public void internalError(final BotException e) {
		telegramClient.sendMessage(new TelegramRequest(e.getMessage(), e.getChatId()));
	}
}

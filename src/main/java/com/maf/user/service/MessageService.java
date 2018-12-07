package com.maf.user.service;


import com.maf.telegram.Message;
import com.maf.user.exception.BotException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;


@Service
public class MessageService {

	private final BotCommandService botCommandService;

	public MessageService(BotCommandService botCommandService) {
		this.botCommandService = botCommandService;
	}

	public void parseMessage(Message message) {

		if (message.getText() != null) {
			try {
				checkOrder(message);


				switch (CallBackData.getByVal(message.getText(), message.getChat().getId())) {
					case START:
						botCommandService.start(message);
						break;
					case MENU:
						botCommandService.menu(message);
						break;
					case CONTACTS:
						botCommandService.contacts(message);
						break;
					case MAKE_ORDER:
						botCommandService.makeOrder(message);
						break;
					default:
						throw new BotException(message.getChat().getId(), "Internal system error");
				}

			} catch (IOException ex) {
				throw new BotException(message.getChat().getId(), "Internal server error");
			}
		}


	}

	private void checkOrder(Message message) throws IOException {

	}

}



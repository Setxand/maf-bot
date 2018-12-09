package com.maf.user.service;


import com.maf.telegram.Message;
import com.maf.user.exception.BotException;
import com.maf.user.model.User;
import com.maf.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class MessageService {

	private final BotCommandService botCommandService;
	private final UserRepository userRepo;

	public MessageService(BotCommandService botCommandService, UserRepository userRepo) {
		this.botCommandService = botCommandService;
		this.userRepo = userRepo;
	}

	public void parseMessage(Message message) {

		if (message.getText() != null) {
			try {
				User user = getUser(message);
				if (user.getStatus() != null) {
					parseUserStatus(message, user);
					return;
				}

				switch (TelegramTextCommands.getByVal(message.getText(), message.getChat().getId())) {
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
					case MAKE_ORDER_DELIVERY:
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

	private void parseUserStatus(Message message, User user) {
		switch (user.getStatus()) {
			case ORDER_1:
				botCommandService.makeOrder1(message);
				break;
			case ORDER_2:
				botCommandService.makeOrder2(message);
				break;
			case ORDER_3:
				botCommandService.makeOrder3(message);
				break;
			case ORDER_4:
				botCommandService.makeOrder4(message);
				break;
			case ORDER_1_DELIVERY:
				botCommandService.makeOrder1(message);
				break;
			default:
				throw new BotException(message.getChat().getId(), "Internal server error");
		}
	}

	private User getUser(Message message) {
		return userRepo.findById(message.getChat().getId()).orElseGet(() -> {
			User user = new User();
			user.setChatId(message.getChat().getId());
			return userRepo.saveAndFlush(user);
		});
	}
}



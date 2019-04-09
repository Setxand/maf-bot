package com.maf.user.service;

import com.maf.user.exception.BotException;
import com.maf.user.model.User;
import com.maf.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import telegram.Message;

import java.io.IOException;


@Service
public class MessageService {

	private final BotCommandService botCommandService;
	private final UserRepository userRepo;
	private final BotCommandOrderService orderService;

	public MessageService(BotCommandService botCommandService, UserRepository userRepo, BotCommandOrderService orderService) {
		this.botCommandService = botCommandService;
		this.userRepo = userRepo;
		this.orderService = orderService;
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
						orderService.makeOrder(message);
						break;
					case MAKE_ORDER_DELIVERY:
						orderService.makeOrder(message);
						break;
					case BROADCAST:
						botCommandService.broadcast(message);
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
				orderService.makeOrder1(message);
				break;
			case ORDER_2:
				orderService.makeOrder2(message);
				break;
			case ORDER_3:
				orderService.makeOrder3(message);
				break;
			case ORDER_4:
				orderService.makeOrder4(message);
				break;
			case ORDER_1_DELIVERY:
				orderService.makeOrder1(message);
				break;
			case BROADCAST_1:
				botCommandService.broadcast1(message);
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



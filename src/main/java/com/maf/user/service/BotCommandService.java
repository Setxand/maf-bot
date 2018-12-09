package com.maf.user.service;


import com.maf.telegram.Chat;
import com.maf.telegram.Message;
import com.maf.telegram.button.KeyboardButton;
import com.maf.telegram.client.TelegramClient;
import com.maf.user.exception.BotException;
import com.maf.user.model.User;
import com.maf.user.model.UserStatus;
import com.maf.user.repository.UserRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

import static com.maf.user.model.UserStatus.ORDER_FINISH;
import static com.maf.user.model.UserStatus.ORDER_FINISH_CANCEL;
import static com.maf.user.service.Buttons.CANCEL;


@Service
public class BotCommandService {

	private final TelegramClient telegramClient;
	private final BotCommandHelperService helperService;
	private final UserRepository userRepo;

	public BotCommandService(TelegramClient telegramClient, BotCommandHelperService helperService, UserRepository userRepo) {
		this.telegramClient = telegramClient;
		this.helperService = helperService;
		this.userRepo = userRepo;
	}


	public void start(Message message) {
		telegramClient.sendKeyboardButtons(message, getMainButtons(), "Hi!");
	}

	public void menu(Message message) throws IOException {
		String msg = StreamUtils
				.copyToString(new ClassPathResource("menu.txt").getInputStream(), Charset.defaultCharset());
		telegramClient.simpleMessage(msg, message);

	}

	public void contacts(Message message) throws IOException {
		String msg = StreamUtils.copyToString(new ClassPathResource("contacts.txt")
				.getInputStream(), Charset.defaultCharset());
		telegramClient.simpleMessage(msg, message);
	}

	@Transactional
	public void makeOrder(Message message) throws IOException {
		User user = getUser(message);
		user.setStatus(UserStatus.ORDER_1);
		menu(message);
		telegramClient.simpleMessage(getBundle(user.getStatus().name()), message);
	}

	@Transactional
	public void makeOrder1(Message message) {
		orderStep(UserStatus.ORDER_2, message);
		telegramClient.simpleMessage(getBundle(UserStatus.ORDER_2.name()), message);
	}

	@Transactional
	public void makeOrder2(Message message) {
		orderStep(UserStatus.ORDER_3, message);
		telegramClient.simpleMessage(getBundle(UserStatus.ORDER_3.name()), message);
	}

	@Transactional
	public void makeOrder3(Message message) {
		orderStep(UserStatus.ORDER_4, message);
		List<KeyboardButton> buttons = Arrays.asList(new KeyboardButton(Buttons.ORDER.getValue()),
				new KeyboardButton(CANCEL.name()));

		telegramClient.sendKeyboardButtons(message, Collections.singletonList(buttons), getBundle(UserStatus.ORDER_4.name()));
	}

	@Transactional
	public void makeOrder4(Message message) {
		User user = getUser(message);
		String mainButtonsText = "";
		if (message.getText().equals(Buttons.ORDER.getValue())) {
			telegramClient.simpleMessage(user.getOrderCheck(), new Message(new Chat(388073901)));//593682738
			mainButtonsText = getBundle(ORDER_FINISH.name());
		} else {
			user.setOrderCheck(null);
			user.setStatus(null);
			mainButtonsText = getBundle(ORDER_FINISH_CANCEL.name());
		}

		telegramClient.sendKeyboardButtons(message, getMainButtons(), getBundle(mainButtonsText));
	}

	private List<List<KeyboardButton>> getMainButtons() {
		return Arrays.stream(TelegramTextCommands.values())
				.filter(c -> !c.getValue().startsWith("/"))
				.map(c -> Collections.singletonList(new KeyboardButton(c.getValue())))
				.collect(Collectors.toList());
	}

	private void orderStep(UserStatus status, Message message) {
		User user = getUser(message);
		user.setStatus(status);
		String text = "\n\n" + message.getText();
		user.setOrderCheck(user.getOrderCheck() != null ? user.getOrderCheck() + text : "New order!" + text);
	}

	private User getUser(Message message) {
		return userRepo.findById(message.getChat().getId())
				.orElseThrow(() -> new BotException(message.getChat().getId(), "Invalid user"));
	}

	private String getBundle(String bundle) {
		return ResourceBundle.getBundle("messages", new Locale("ua", "UA"))
				.getString(bundle);
	}
}

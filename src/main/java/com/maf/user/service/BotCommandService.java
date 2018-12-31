package com.maf.user.service;


import com.maf.telegram.Chat;
import com.maf.telegram.Message;
import com.maf.telegram.button.KeyboardButton;
import com.maf.telegram.client.TelegramClient;
import com.maf.user.exception.BotException;
import com.maf.user.model.User;
import com.maf.user.repository.UserRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

import static com.maf.user.model.User.Role.ADMIN;
import static com.maf.user.model.UserStatus.*;
import static com.maf.user.service.TelegramTextCommands.BROADCAST;


@Service
public class BotCommandService {

	private static final int DEFAULT_PAGE_SIZE = 30;

	private final TelegramClient telegramClient;
	private final UserRepository userRepo;

	public BotCommandService(TelegramClient telegramClient, UserRepository userRepo) {
		this.telegramClient = telegramClient;
		this.userRepo = userRepo;
	}


	public void start(Message message) {
		telegramClient.sendKeyboardButtons(message, getMainButtons(message.getChat().getId()), "Hi!");
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
	public void broadcast(Message message) {
		User user = getUser(message);
		user.setStatus(BROADCAST_1);
		telegramClient.simpleMessage(getBundle(BROADCAST_1.name()), message);
	}

	public void broadcast1(Message message) {
		new Thread() {
			public void run() {

				boolean next = true;
				int number = 0;

				while (next) {
					Page<User> page = userRepo.findAll(PageRequest.of(number, DEFAULT_PAGE_SIZE));
					page.forEach(p -> telegramClient.simpleMessage(message.getText(), new Message(new Chat(p.getChatId()))));

					try {

						Thread.sleep(1000);

					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}

					number = page.getNumber() + 1;
					next = page.hasNext();
				}
			}
		}.start();

		User user = getUser(message);
		user.setStatus(null);
		userRepo.saveAndFlush(user);
	}

	public List<List<KeyboardButton>> getMainButtons(Integer id) {
		List<List<KeyboardButton>> buttons = Arrays.stream(TelegramTextCommands.values())
				.filter(c -> !c.getValue().startsWith("/") && c != BROADCAST)
				.map(c -> Collections.singletonList(new KeyboardButton(c.getValue())))
				.collect(Collectors.toList());

		// Add admin`s buttons (if admin)
		buttons.addAll(Arrays.stream(TelegramTextCommands.values())
				.filter(b -> b == BROADCAST &&
						userRepo.findByRole(ADMIN).stream().anyMatch(a -> a.getChatId().equals(id)))
				.filter(c -> !c.getValue().startsWith("/"))
				.map(c -> Collections.singletonList(new KeyboardButton(c.getValue())))
				.collect(Collectors.toList()));

		return buttons;
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

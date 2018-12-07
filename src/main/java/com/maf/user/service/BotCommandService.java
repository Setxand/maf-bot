package com.maf.user.service;


import com.maf.telegram.Message;
import com.maf.telegram.button.KeyboardButton;
import com.maf.telegram.client.TelegramClient;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class BotCommandService {

	private final TelegramClient telegramClient;
	private final BotCommandHelperService helperService;
	public BotCommandService(TelegramClient telegramClient, BotCommandHelperService helperService) {
		this.telegramClient = telegramClient;
		this.helperService = helperService;
	}


	public void start(Message message) {
		List<List<KeyboardButton>> buttons = Arrays.stream(CallBackData.values())
				.filter(c -> !c.getValue().startsWith("/")).map(c -> Collections.singletonList(new KeyboardButton(c.getValue())))
				.collect(Collectors.toList());

		telegramClient.sendKeyboardButtons(message, buttons, "Hi!");
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

	public void makeOrder(Message message) throws IOException {
//		telegramClient.simpleMessage("✏️Напиши своє замовлення нижче\uD83D\uDC47\uD83C\uDFFC[в одному повідомленні]\n" +
//				"\n" +
//				"(наприклад, паніні з куркою і велике лате)", message);
//
//		List<String> lines = Collections.singletonList("ORDER");
//		Path file = Paths.get("src\\main\\resources\\orders\\" + message.getChat().getId() + "_order.txt");
//
//		Files.write(file, lines, Charset.forName("UTF-8"));
	}
}

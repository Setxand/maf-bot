package com.maf;

import com.maf.telegram.client.TelegramClient;
import com.maf.user.model.User;
import com.maf.user.repository.UserRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
public class BotStarting {

	private final String[] admins = {"388073901"};

	private final TelegramClient telegramClient;
	private final UserRepository userRepo;

	public BotStarting(TelegramClient telegramClient, UserRepository userRepo) {
		this.telegramClient = telegramClient;
		this.userRepo = userRepo;
	}

	@PostConstruct
	public void init() {
		telegramClient.setWebHook();

		Arrays.stream(admins).forEach(a -> {
			Integer admin = Integer.parseInt(a);
			User user = userRepo.findById(admin).orElseGet(User::new);
			user.setChatId(admin);
			user.setRole(User.Role.ADMIN);
			userRepo.saveAndFlush(user);
		});
	}
}

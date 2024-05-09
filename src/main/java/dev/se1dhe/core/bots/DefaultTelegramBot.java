package dev.se1dhe.core.bots;


import dev.se1dhe.core.bots.AbstractTelegramBot;
import org.telegram.telegrambots.meta.generics.TelegramClient;


public class DefaultTelegramBot extends AbstractTelegramBot {
	public DefaultTelegramBot(TelegramClient telegramClient) {
		super(telegramClient);
	}
}

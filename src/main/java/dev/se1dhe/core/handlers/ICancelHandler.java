package dev.se1dhe.core.handlers;

import dev.se1dhe.core.bots.AbstractTelegramBot;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public interface ICancelHandler extends ITelegramHandler {
	/**
	 * Fired whenever user types in /cancel command to cancel the current action
	 *
	 * @param bot     the bot
	 * @param update  the update
	 * @param message the message
	 * @throws TelegramApiException the exception
	 */
	void onCancel(AbstractTelegramBot bot, Update update, Message message) throws TelegramApiException;
}

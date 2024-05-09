package dev.se1dhe.core.handlers;

import dev.se1dhe.core.bots.AbstractTelegramBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public interface IUpdateHandler extends ITelegramHandler {
	/**
	 * Fired any update is received
	 *
	 * @param bot    the bot
	 * @param update the update
	 * @return {@code true} if handler 'consumed' that event, aborting notification to other handlers, {@code false} otherwise, continuing to look for handler that would return {@code true}
	 * @throws TelegramApiException the exception
	 */
	boolean onUpdate(AbstractTelegramBot bot, Update update) throws TelegramApiException;
}